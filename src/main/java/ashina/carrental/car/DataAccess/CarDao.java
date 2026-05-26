package ashina.carrental.car.DataAccess;

import ashina.carrental.car.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarDao extends JpaRepository<Car, Integer> {
    Optional<Car> findCarById(int id);
}
