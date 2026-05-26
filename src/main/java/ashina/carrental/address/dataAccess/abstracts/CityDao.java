package ashina.carrental.address.dataAccess.abstracts;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import ashina.carrental.address.entities.concretes.City;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface CityDao extends JpaRepository<City, Integer>{
	City findCityByCityId(int cityId);
	List<City> findCitiesByCountryCountryId (int countryId);
	Optional<City> findCityByCityIdOrCityName(int cityId, String cityName);
	City findCityByCityName(String cityName);
	City findByCityName(String cityName);
	boolean existsByCityName(String cityName);
	List<City> findCitiesByCountryCountryName(String countryName);
	void deleteCityByCityNameOrCityId(String cityName,int cityId);
}
