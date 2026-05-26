package ashina.carrental.renter;

import ashina.carrental.auth.customer.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * A rent-a-car listing posted by a rental company (an {@link AppUser}).
 * Visible publicly via {@code PublicListingController}; the owner manages
 * their own via {@code RenterController}.
 */
@Entity
@Table(name = "listing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "brand", length = 80)
    private String brand;

    @Column(name = "model", length = 120)
    private String model;

    @Column(name = "model_year")
    private Integer year;

    @Column(name = "kilometers")
    private Integer km;

    @Column(name = "price_per_day")
    private Integer pricePerDay;

    @Column(name = "color", length = 40)
    private String color;

    @Column(name = "city", length = 80)
    private String city;

    @Column(name = "transmission", length = 40)
    private String transmission;

    @Column(name = "fuel", length = 40)
    private String fuel;

    @Column(name = "seats")
    private Integer seats;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "listing_photo", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "url", length = 500)
    private List<String> photoUrls = new ArrayList<>();

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** The rental company that owns this listing. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnoreProperties({"passwordHash", "hibernateLazyInitializer", "handler"})
    private AppUser owner;
}
