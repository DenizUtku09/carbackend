package ashina.carrental.car.business.concretes;

import ashina.carrental.car.DataAccess.CarBrandDao;
import ashina.carrental.car.DataAccess.CarDao;
import ashina.carrental.car.business.abstracts.CarBrandService;
import ashina.carrental.car.entities.Car;
import ashina.carrental.car.entities.CarBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarBrandManager implements CarBrandService {
    private final CarBrandDao carBrandDao;
    private final CarDao carDao;
    @Autowired
    public CarBrandManager(CarBrandDao carBrandDao,CarDao carDao){
        super();
        this.carBrandDao=carBrandDao;
        this.carDao=carDao;
    }

    @Override
    public CarBrand addCarBrand(String brandName) {
        Optional<CarBrand> existingCarBrand=carBrandDao.findCarBrandByBrandName(brandName);
        if(existingCarBrand.isPresent()){throw new RuntimeException("This car brand already exists by name.");}

        CarBrand addedCarBrand=new CarBrand();
        addedCarBrand.setBrandName(brandName);
        return carBrandDao.save(addedCarBrand);

    }

    @Override
    public CarBrand updateCarBrandByName(String brandName, String brandName2) {
        CarBrand existingCarBrand=carBrandDao.findCarBrandByBrandName(brandName)
                .orElseThrow(() -> new RuntimeException("This car brand does not exist by name."));
        existingCarBrand.setBrandName(brandName2);
        return existingCarBrand;


    }

    @Override
    public CarBrand updateCarBrandById(int id, String brandName) {
        CarBrand existingCarBrand=carBrandDao.findCarBrandById(id)
                .orElseThrow(() -> new RuntimeException("This car brand does not exist by ID."));
        existingCarBrand.setBrandName(brandName);
        return existingCarBrand;
    }

    @Override
    public void deleteCarBrandByName(String brandName) {
        Optional<CarBrand> existingCarBrand=carBrandDao.findCarBrandByBrandName(brandName);

        existingCarBrand.ifPresentOrElse(carBrandDao::delete,() -> {throw new RuntimeException("T");});

    }

    @Override
    public void deleteCarBrandById(int id) {
        Optional<CarBrand> existingCarBrand=carBrandDao.findCarBrandById(id);

        existingCarBrand.ifPresentOrElse(carBrandDao::delete,() -> {throw new RuntimeException("T");});

    }

    @Override
    public List<String> getAllCarBrands() {
        return carBrandDao.findAll()
                .stream()
                .map(CarBrand::getBrandName)
                .collect(Collectors.toList());

    }

    @Override
    public Car addCarBrandToCarById(int carId, int brandId) {
        Car existingCar=carDao.findCarById(carId)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        CarBrand existingCarBrand=carBrandDao.findCarBrandById(brandId)
                .orElseThrow(() -> new RuntimeException("This car brand does not exist by ID."));

        existingCar.setCarBrand(existingCarBrand);
        return existingCar;
    }

    @Override
    public Car addCarBrandToCarByName(int carId, String brandName) {

        Car existingCar=carDao.findCarById(carId)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        CarBrand existingCarBrand=carBrandDao.findCarBrandByBrandName(brandName)
                .orElseThrow(() -> new RuntimeException("This car brand does not exist by name."));

        existingCar.setCarBrand(existingCarBrand);
        return existingCar;

    }

    @Override
    public Car updateCarBrandInCarByName(int carId, String brandName) {
        Car existingCar = carDao.findCarById(carId)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        CarBrand existingCarBrand = carBrandDao.findCarBrandByBrandName(brandName)
                .orElseThrow(() -> new RuntimeException("This car brand does not exist by name."));

        if (existingCar.getCarBrand() == existingCarBrand) {
            throw new RuntimeException("This car brand is same as before.");
        }
        existingCar.setCarBrand(existingCarBrand);
        carDao.save(existingCar);
        return existingCar;
    }

    @Override
    public Car updateCarBrandInCarById(int carId, int brandId) {
        Car existingCar = carDao.findCarById(carId)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        CarBrand existingCarBrand=carBrandDao.findCarBrandById(brandId)
                .orElseThrow(() -> new RuntimeException("This car brand does not exist by ID."));
        if(existingCar.getCarBrand().equals(existingCarBrand)){
            throw new RuntimeException("This car brand is same as before.");
        }
        existingCar.setCarBrand(existingCarBrand);
        carDao.save(existingCar);
        return existingCar;
    }

    public Car deleteCarBrandInCar(int carId){
        Car existingCar = carDao.findCarById(carId)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));

        existingCar.setCarBrand(null);
        return existingCar;

    }

}
