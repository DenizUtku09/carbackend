package ashina.carrental.address.dataAccess.abstracts;


import org.springframework.data.jpa.repository.JpaRepository;

import ashina.carrental.address.entities.concretes.Country;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Component
@Repository
public interface CountryDao extends JpaRepository<Country,Integer> {
	Optional<Country> findCountryByCountryId(int countryId);
	Optional<Country> findCountryByCountryName(String countryName);
	void deleteCountryByCountryName(String countryName);
	void deleteCountryByCountryId(int countryId);
	Country findByCountryName(String countryName);
}
