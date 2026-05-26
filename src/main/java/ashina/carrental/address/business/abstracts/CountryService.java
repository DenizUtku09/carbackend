package ashina.carrental.address.business.abstracts;

import java.util.List;
import java.util.Optional;

import ashina.carrental.address.entities.concretes.City;
import ashina.carrental.address.entities.concretes.Country;
import ashina.carrental.address.entities.concretes.dtos.CityDTO;
import ashina.carrental.address.entities.concretes.dtos.CountryDTO;
import ashina.carrental.address.entities.concretes.dtos.requests.country.*;


public interface CountryService {
	CountryDTO addCountry(AddCountryRequest addCountryRequest, CountryDTO countryDTO);
	void updateCountryByName(String countryName, CountryUpdateRequest updateRequest, CountryDTO countryDTO);
	void updateCountryById(int countryId, CountryUpdateRequest updateRequest,CountryDTO countryDTO);
	void deleteCountryByName(DeleteCountryByNameRequest deleteCountryByNameRequest);
	void deleteCountryById(DeleteCountryByIdRequest deleteCountryByIdRequest);
	CityDTO addCityToCountryByName(String countryName,AddCityToCountryRequest addCityToCountryRequest);
	CityDTO addCityToCountryById(int countryId,AddCityToCountryRequest addCityToCountryRequest);
	void updateCitiesInCountryById(int countryId, String cityName, UpdateCityInCountryRequest updateCityInCountryRequest);
	void updateCitiesInCountryByName(String countryName,String cityName,UpdateCityInCountryRequest updateCityInCountryRequest);
	void deleteCityInCountryByName(String countryName, DeleteCityInCountryRequest deleteCityInCountryRequest);
	void deleteCityInCountryById(int countryId,DeleteCityInCountryRequest deleteCityInCountryRequest);
	List<CountryDTO> getAllCountries();
	Optional<Country> getCountryById(int countryId);
	Optional<Country> getCountryByName(String countryName);
	List<City> getCitiesInCountryByName(String countryName);
	List<City> getCitiesInCountryById(int countryId);






	
	
	
	

	
	

}
