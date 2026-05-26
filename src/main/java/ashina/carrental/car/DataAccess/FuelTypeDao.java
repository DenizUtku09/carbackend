package ashina.carrental.car.DataAccess;

import ashina.carrental.car.entities.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeDao extends JpaRepository<FuelType, Integer> {
}
