package ashina.carrental.renter;

import ashina.carrental.auth.customer.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByOwnerOrderByCreatedAtDesc(AppUser owner);

    long countByOwner(AppUser owner);

    @Query("SELECT COALESCE(SUM(l.viewCount), 0) FROM Listing l WHERE l.owner = :owner")
    long sumViewsByOwner(AppUser owner);

    List<Listing> findAllByOrderByCreatedAtDesc();
}
