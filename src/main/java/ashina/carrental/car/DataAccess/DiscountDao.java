package ashina.carrental.car.DataAccess;


import ashina.carrental.car.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountDao extends JpaRepository<Discount, Integer> {
}
