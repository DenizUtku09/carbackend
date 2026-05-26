package ashina.carrental.renter;

import ashina.carrental.auth.customer.AppUser;
import ashina.carrental.auth.customer.AppUserRepository;
import ashina.carrental.renter.dto.CreateListingRequest;
import ashina.carrental.renter.dto.RenterStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Private API for a logged-in rental company ("renter"). Every endpoint is
 * scoped to the JWT subject — a renter only sees and edits their own data.
 */
@RestController
@RequestMapping("/api/renter")
@RequiredArgsConstructor
public class RenterController {

    private final ListingRepository listings;
    private final MessageRepository messages;
    private final AppUserRepository users;

    /* ===== Listings ===== */

    @GetMapping("/listings")
    public ResponseEntity<List<Listing>> myListings(Authentication auth) {
        AppUser owner = requireUser(auth);
        return ResponseEntity.ok(listings.findByOwnerOrderByCreatedAtDesc(owner));
    }

    @PostMapping("/listings")
    public ResponseEntity<Listing> create(@RequestBody CreateListingRequest req, Authentication auth) {
        AppUser owner = requireUser(auth);
        validate(req);
        Listing l = new Listing();
        l.setTitle(req.title().trim());
        l.setBrand(emptyToNull(req.brand()));
        l.setModel(emptyToNull(req.model()));
        l.setYear(req.year());
        l.setKm(req.km());
        l.setPricePerDay(req.pricePerDay());
        l.setColor(emptyToNull(req.color()));
        l.setCity(emptyToNull(req.city()));
        l.setTransmission(emptyToNull(req.transmission()));
        l.setFuel(emptyToNull(req.fuel()));
        l.setSeats(req.seats());
        l.setDescription(req.description());
        l.setPhotoUrls(req.photoUrls() == null ? new ArrayList<>() : new ArrayList<>(req.photoUrls()));
        l.setCreatedAt(Instant.now());
        l.setOwner(owner);
        Listing saved = listings.save(l);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/listings/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        AppUser owner = requireUser(auth);
        Listing l = listings.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing not found."));
        if (!l.getOwner().getId().equals(owner.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your listing.");
        }
        // Drop any messages first so the FK constraint doesn't bite.
        messages.deleteByListing(l);
        listings.delete(l);
        return ResponseEntity.noContent().build();
    }

    /* ===== Stats ===== */

    @GetMapping("/stats")
    public ResponseEntity<RenterStatsResponse> stats(Authentication auth) {
        AppUser owner = requireUser(auth);
        List<Listing> mine = listings.findByOwnerOrderByCreatedAtDesc(owner);
        long total = mine.size();
        long views = mine.stream().mapToInt(Listing::getViewCount).sum();
        double avg = total == 0 ? 0.0 : ((double) views) / total;
        long unread = messages.countUnreadByOwner(owner);
        long totalMsg = messages.findByOwner(owner).size();
        List<RenterStatsResponse.TopListing> top = mine.stream()
                .sorted(Comparator.comparingInt(Listing::getViewCount).reversed())
                .limit(5)
                .map(l -> new RenterStatsResponse.TopListing(l.getId(), l.getTitle(), l.getViewCount()))
                .toList();
        return ResponseEntity.ok(new RenterStatsResponse(total, views, unread, totalMsg, avg, top));
    }

    /* ===== Messages ===== */

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> inbox(Authentication auth) {
        AppUser owner = requireUser(auth);
        return ResponseEntity.ok(messages.findByOwner(owner));
    }

    @PostMapping("/messages/{id}/read")
    public ResponseEntity<Message> markRead(@PathVariable Long id, Authentication auth) {
        AppUser owner = requireUser(auth);
        Message m = messages.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found."));
        if (!m.getListing().getOwner().getId().equals(owner.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your message.");
        }
        if (m.getReadAt() == null) {
            m.setReadAt(Instant.now());
            m = messages.save(m);
        }
        return ResponseEntity.ok(m);
    }

    /* ===== helpers ===== */

    private AppUser requireUser(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sign in to access the renter panel.");
        }
        return users.findByEmail(auth.getName().trim().toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account not found."));
    }

    private static void validate(CreateListingRequest req) {
        if (req == null) throw bad("Missing body.");
        if (req.title() == null || req.title().trim().isEmpty()) throw bad("title is required.");
        if (req.pricePerDay() == null || req.pricePerDay() < 0) throw bad("pricePerDay must be ≥ 0.");
        if (req.year() != null && (req.year() < 1900 || req.year() > 2100)) throw bad("year out of range.");
    }

    private static String emptyToNull(String s) { return (s == null || s.trim().isEmpty()) ? null : s.trim(); }
    private static ResponseStatusException bad(String msg) { return new ResponseStatusException(HttpStatus.BAD_REQUEST, msg); }
}
