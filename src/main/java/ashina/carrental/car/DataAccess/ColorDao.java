package ashina.carrental.car.DataAccess;

import ashina.carrental.car.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorDao extends JpaRepository<Color,Integer> {
    Optional<Color> findColorByColorName(String colorName);
    Optional<Color> findColorById(int id);
    Optional<Color> findColorByIdOrColorName(int id,String colorName);
}
