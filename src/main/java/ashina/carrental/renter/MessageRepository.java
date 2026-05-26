package ashina.carrental.renter;

import ashina.carrental.auth.customer.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.listing.owner = :owner ORDER BY m.createdAt DESC")
    List<Message> findByOwner(AppUser owner);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.listing.owner = :owner AND m.readAt IS NULL")
    long countUnreadByOwner(AppUser owner);

    @Modifying
    @Transactional
    void deleteByListing(Listing listing);
}
