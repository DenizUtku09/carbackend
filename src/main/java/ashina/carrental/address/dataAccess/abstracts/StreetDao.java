package ashina.carrental.address.dataAccess.abstracts;
import ashina.carrental.address.entities.concretes.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Component
public interface StreetDao extends JpaRepository<Street,Integer> {
    List<Street> findStreetsByCityCityName(String cityName);
    List<Street> findStreetsByCityCityId(int cityId);
    Street findStreetByStreetName(String streetName);
    Street findByStreetName(String streetName);
    Street findByStreetId(int streetId);
    boolean existsByStreetName(String streetName);
    boolean existsByStreetId(int streetId);
    Street findStreetByStreetId(int streetId);
    void deleteByStreetName(String streetName);
    void deleteByStreetId(int streetId);
}
