package ashina.carrental.admin;

import ashina.carrental.address.dataAccess.abstracts.AddressDao;
import ashina.carrental.address.dataAccess.abstracts.BuildingNumberDao;
import ashina.carrental.address.dataAccess.abstracts.CityDao;
import ashina.carrental.address.dataAccess.abstracts.CountryDao;
import ashina.carrental.address.dataAccess.abstracts.StreetDao;
import ashina.carrental.auth.customer.AppUserRepository;
import ashina.carrental.car.DataAccess.CarBrandDao;
import ashina.carrental.car.DataAccess.CarDao;
import ashina.carrental.car.DataAccess.CarModelDao;
import ashina.carrental.car.DataAccess.CarTypeDao;
import ashina.carrental.car.DataAccess.ColorDao;
import ashina.carrental.car.DataAccess.DiscountDao;
import ashina.carrental.car.DataAccess.FuelTypeDao;
import ashina.carrental.car.DataAccess.InsuranceDao;
import ashina.carrental.car.DataAccess.PriceDao;
import ashina.carrental.car.DataAccess.TransmissionTypeDao;
import ashina.carrental.car.entities.Car;
import ashina.carrental.car.entities.CarType;
import ashina.carrental.car.entities.Discount;
import ashina.carrental.car.entities.FuelType;
import ashina.carrental.car.entities.Insurance;
import ashina.carrental.car.entities.Price;
import ashina.carrental.car.entities.TransmissionType;
import ashina.carrental.dataAccess.abstracts.JobDao;
import ashina.carrental.dataAccess.abstracts.UsersDaos.EmployeeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Single admin-side entry point for everything the admin panel needs that
 * doesn't already have a dedicated controller. Fills in CRUD for the value
 * tables (car types, fuel types, transmission types, insurances, discounts)
 * and provides a stats endpoint plus user management.
 *
 * <p>Existing controllers ({@code /api/jobs}, {@code /api/carBrands}, etc.) are
 * still the source of truth for their entities — this controller doesn't
 * shadow them, it complements them.</p>
 *
 * <p>Auth: every request requires authentication. Today that's either
 * admin/admin Basic auth or a customer JWT; for production you'd add a
 * {@code @PreAuthorize("hasRole('ADMIN')")} guard.</p>
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    // Address
    private final CountryDao countryDao;
    private final CityDao cityDao;
    private final StreetDao streetDao;
    private final BuildingNumberDao buildingNumberDao;
    private final AddressDao addressDao;

    // Operations
    private final JobDao jobDao;
    private final EmployeeDao employeeDao;

    // Catalog
    private final CarDao carDao;
    private final CarBrandDao carBrandDao;
    private final CarModelDao carModelDao;
    private final CarTypeDao carTypeDao;
    private final ColorDao colorDao;
    private final FuelTypeDao fuelTypeDao;
    private final TransmissionTypeDao transmissionTypeDao;
    private final InsuranceDao insuranceDao;

    // Pricing
    private final PriceDao priceDao;
    private final DiscountDao discountDao;

    // Customers
    private final AppUserRepository appUserRepository;


    /* =========================================================
     *  STATS
     * ========================================================= */

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> stats() {
        Instant weekAgo = Instant.now().minus(7, ChronoUnit.DAYS);

        // Pull users once — we use the rows for both counts and recent list.
        List<ashina.carrental.auth.customer.AppUser> allUsers = appUserRepository.findAll();
        long newUsersThisWeek = allUsers.stream()
                .filter(u -> u.getCreatedAt() != null && u.getCreatedAt().isAfter(weekAgo))
                .count();
        var recentUsers = allUsers.stream()
                .sorted((a, b) -> b.getCreatedAt() == null ? 0
                        : (a.getCreatedAt() == null ? 1 : b.getCreatedAt().compareTo(a.getCreatedAt())))
                .limit(5)
                .map(u -> Map.of(
                        "id", u.getId(),
                        "email", u.getEmail(),
                        "fullName", u.getFullName() == null ? "" : u.getFullName(),
                        "createdAt", u.getCreatedAt() == null ? "" : u.getCreatedAt().toString()))
                .toList();

        Map<String, Object> categories = new LinkedHashMap<>();
        categories.put("customers", Map.of(
                "users", allUsers.size(),
                "newThisWeek", newUsersThisWeek));
        categories.put("operations", Map.of(
                "jobs", jobDao.count(),
                "employees", employeeDao.count()));
        categories.put("fleet", Map.of(
                "cars", carDao.count(),
                "brands", carBrandDao.count(),
                "models", carModelDao.count(),
                "colors", colorDao.count()));
        categories.put("catalog", Map.of(
                "carTypes", carTypeDao.count(),
                "fuelTypes", fuelTypeDao.count(),
                "transmissionTypes", transmissionTypeDao.count(),
                "insurances", insuranceDao.count()));
        categories.put("pricing", Map.of(
                "prices", priceDao.count(),
                "discounts", discountDao.count()));
        categories.put("address", Map.of(
                "countries", countryDao.count(),
                "cities", cityDao.count(),
                "streets", streetDao.count(),
                "buildingNumbers", buildingNumberDao.count(),
                "addresses", addressDao.count()));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("generatedAt", Instant.now().toString());
        response.put("categories", categories);
        response.put("totals", Map.of(
                "users", allUsers.size(),
                "rowsOverall",
                        allUsers.size() + jobDao.count() + employeeDao.count()
                        + carDao.count() + carBrandDao.count() + carModelDao.count() + colorDao.count()
                        + carTypeDao.count() + fuelTypeDao.count() + transmissionTypeDao.count() + insuranceDao.count()
                        + priceDao.count() + discountDao.count()
                        + countryDao.count() + cityDao.count() + streetDao.count() + buildingNumberDao.count() + addressDao.count()));
        response.put("recentUsers", recentUsers);
        return ResponseEntity.ok(response);
    }


    /* =========================================================
     *  USERS (AppUser admin management)
     * ========================================================= */

    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> listUsers() {
        // Strip password_hash before returning.
        List<Map<String, Object>> users = appUserRepository.findAll().stream()
                .map(u -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", u.getId());
                    m.put("email", u.getEmail());
                    m.put("fullName", u.getFullName());
                    m.put("createdAt", u.getCreatedAt() == null ? null : u.getCreatedAt().toString());
                    return m;
                })
                .toList();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!appUserRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User #" + id + " does not exist.");
        }
        appUserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /* =========================================================
     *  CARS
     * ========================================================= */

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> listCars() {
        return ResponseEntity.ok(carDao.findAll());
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
        if (!carDao.existsById(id)) throw notFound("Car", id);
        carDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /* =========================================================
     *  CAR TYPES (value table)
     * ========================================================= */

    public record TypeBody(String type) {}

    @GetMapping("/car-types")
    public ResponseEntity<List<CarType>> listCarTypes() {
        return ResponseEntity.ok(carTypeDao.findAll());
    }

    @PostMapping("/car-types")
    public ResponseEntity<CarType> addCarType(@RequestBody TypeBody body) {
        requireString("type", body == null ? null : body.type());
        CarType t = new CarType();
        t.setCarType(body.type().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(carTypeDao.save(t));
    }

    @DeleteMapping("/car-types/{id}")
    public ResponseEntity<Void> deleteCarType(@PathVariable Integer id) {
        if (!carTypeDao.existsById(id)) throw notFound("CarType", id);
        carTypeDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /* =========================================================
     *  FUEL TYPES
     * ========================================================= */

    @GetMapping("/fuel-types")
    public ResponseEntity<List<FuelType>> listFuelTypes() {
        return ResponseEntity.ok(fuelTypeDao.findAll());
    }

    @PostMapping("/fuel-types")
    public ResponseEntity<FuelType> addFuelType(@RequestBody TypeBody body) {
        requireString("type", body == null ? null : body.type());
        FuelType t = new FuelType();
        t.setFuelType(body.type().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(fuelTypeDao.save(t));
    }

    @DeleteMapping("/fuel-types/{id}")
    public ResponseEntity<Void> deleteFuelType(@PathVariable Integer id) {
        if (!fuelTypeDao.existsById(id)) throw notFound("FuelType", id);
        fuelTypeDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /* =========================================================
     *  TRANSMISSION TYPES
     * ========================================================= */

    @GetMapping("/transmission-types")
    public ResponseEntity<List<TransmissionType>> listTransmissionTypes() {
        return ResponseEntity.ok(transmissionTypeDao.findAll());
    }

    @PostMapping("/transmission-types")
    public ResponseEntity<TransmissionType> addTransmissionType(@RequestBody TypeBody body) {
        requireString("type", body == null ? null : body.type());
        TransmissionType t = new TransmissionType();
        t.setTransmissionType(body.type().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(transmissionTypeDao.save(t));
    }

    @DeleteMapping("/transmission-types/{id}")
    public ResponseEntity<Void> deleteTransmissionType(@PathVariable Integer id) {
        if (!transmissionTypeDao.existsById(id)) throw notFound("TransmissionType", id);
        transmissionTypeDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /* =========================================================
     *  INSURANCES
     * ========================================================= */

    public record InsuranceBody(Integer year, String company) {}

    @GetMapping("/insurances")
    public ResponseEntity<List<Insurance>> listInsurances() {
        return ResponseEntity.ok(insuranceDao.findAll());
    }

    @PostMapping("/insurances")
    public ResponseEntity<Insurance> addInsurance(@RequestBody InsuranceBody body) {
        if (body == null || body.year() == null || body.year() < 1980 || body.year() > 2100) {
            throw bad("year is required (1980–2100).");
        }
        requireString("company", body.company());
        Insurance ins = new Insurance();
        ins.setInsuranceYear(body.year());
        ins.setInsuranceCompany(body.company().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceDao.save(ins));
    }

    @DeleteMapping("/insurances/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Integer id) {
        if (!insuranceDao.existsById(id)) throw notFound("Insurance", id);
        insuranceDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /* =========================================================
     *  PRICES (read-only — creation is tied to a car via PriceController)
     * ========================================================= */

    @GetMapping("/prices")
    public ResponseEntity<List<Price>> listPrices() {
        return ResponseEntity.ok(priceDao.findAll());
    }


    /* =========================================================
     *  DISCOUNTS
     * ========================================================= */

    public record DiscountBody(Integer rate, String startDate, String endDate) {}

    @GetMapping("/discounts")
    public ResponseEntity<List<Discount>> listDiscounts() {
        return ResponseEntity.ok(discountDao.findAll());
    }

    @PostMapping("/discounts")
    public ResponseEntity<Discount> addDiscount(@RequestBody DiscountBody body) {
        if (body == null || body.rate() == null || body.rate() < 0 || body.rate() > 100) {
            throw bad("rate must be between 0 and 100.");
        }
        Discount d = new Discount();
        d.setDiscountRate(body.rate());
        if (body.startDate() != null && !body.startDate().isBlank()) {
            d.setStartDate(java.time.LocalDateTime.parse(body.startDate()));
        }
        if (body.endDate() != null && !body.endDate().isBlank()) {
            d.setEndDate(java.time.LocalDateTime.parse(body.endDate()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(discountDao.save(d));
    }

    @DeleteMapping("/discounts/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
        if (!discountDao.existsById(id)) throw notFound("Discount", id);
        discountDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    /* =========================================================
     *  helpers
     * ========================================================= */

    private static void requireString(String field, String v) {
        if (v == null || v.trim().isEmpty()) {
            throw bad(field + " is required.");
        }
    }

    private static ResponseStatusException bad(String msg) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

    private static ResponseStatusException notFound(String what, Object id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, what + " #" + id + " does not exist.");
    }
}
