package ashina.carrental.renter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * A message a (potentially anonymous) customer sends about a {@link Listing}.
 * The listing's owner reads them in their renter inbox; replying out-of-band
 * (phone/email) is on them — we don't model a back-and-forth thread here.
 */
@Entity
@Table(name = "listing_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "from_name", length = 120)
    private String fromName;

    @Column(name = "from_email", length = 254)
    private String fromEmail;

    @Column(name = "from_phone", length = 40)
    private String fromPhone;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "owner", "photoUrls", "description"})
    private Listing listing;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "read_at")
    private Instant readAt;
}
