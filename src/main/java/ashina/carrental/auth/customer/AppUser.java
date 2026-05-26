package ashina.carrental.auth.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Customer-side account used by the marketplace and rental sites.
 *
 * <p>Separate from {@code Employee} (which is an internal staff record).
 * The password column stores a Spring-Security-prefixed encoded value
 * (typically {@code {bcrypt}...}); never plain text.</p>
 */
@Entity
@Table(name = "app_user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, length = 254)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    @Column(name = "full_name", length = 120)
    private String fullName;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
