package ashina.carrental.renter;

import ashina.carrental.renter.dto.SendMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

/**
 * Public endpoints — anyone (anonymous or signed-in) can browse listings
 * and contact a listing's owner.
 *
 * <p>Viewing a listing's detail increments its view counter, which is what
 * the owner sees in their renter dashboard stats.</p>
 */
@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class PublicListingController {

    private final ListingRepository listings;
    private final MessageRepository messages;

    @GetMapping
    public ResponseEntity<List<Listing>> all() {
        return ResponseEntity.ok(listings.findAllByOrderByCreatedAtDesc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> view(@PathVariable Long id) {
        Listing l = listings.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing not found."));
        l.setViewCount(l.getViewCount() + 1);
        listings.save(l);
        return ResponseEntity.ok(l);
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity<Message> contact(@PathVariable Long id, @RequestBody SendMessageRequest req) {
        Listing l = listings.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Listing not found."));
        if (req == null || req.content() == null || req.content().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message body is required.");
        }
        Message m = new Message();
        m.setListing(l);
        m.setFromName(emptyToNull(req.fromName()));
        m.setFromEmail(emptyToNull(req.fromEmail()));
        m.setFromPhone(emptyToNull(req.fromPhone()));
        m.setContent(req.content().trim());
        m.setCreatedAt(Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(messages.save(m));
    }

    private static String emptyToNull(String s) { return (s == null || s.trim().isEmpty()) ? null : s.trim(); }
}
