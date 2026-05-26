package ashina.carrental.car.DataAccess;



import ashina.carrental.car.entities.Car;
import ashina.carrental.car.entities.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarModelDao extends JpaRepository<CarModel, Integer> {

    Optional<CarModel> findCarModelById(int id);
    Optional<CarModel> findCarModelByModelName(String modelName);
    Optional<CarModel> findCarModelsByCarBrandId(int id);
 }
