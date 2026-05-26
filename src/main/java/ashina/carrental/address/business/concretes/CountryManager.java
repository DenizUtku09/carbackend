package ashina.carrental.address.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import ashina.carrental.address.entities.concretes.dtos.CityDTO;
import ashina.carrental.address.entities.concretes.dtos.CountryDTO;
import ashina.carrental.address.entities.concretes.dtos.mappers.CityDTOMapper;
import ashina.carrental.address.entities.concretes.dtos.mappers.CountryDTOMapper;
import ashina.carrental.address.entities.concretes.dtos.requests.country.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ashina.carrental.address.business.abstracts.CountryService;
import ashina.carrental.address.dataAccess.abstracts.CityDao;
import ashina.carrental.address.dataAccess.abstracts.CountryDao;
import ashina.carrental.address.entities.concretes.City;
import ashina.carrental.address.entities.concretes.Country;
@Service
@Transactional
public class CountryManager implements CountryService {

	private final CountryDTOMapper countryDTOMapper;
	private final CityDTOMapper cityDTOMapper;
	private final CountryDao countryDao;
	private final CityDao cityDao;

	@Autowired
	public CountryManager(CityDao cityDao, CountryDao countryDao, CountryDTOMapper countryDTOMapper,CityDTOMapper cityDTOMapper) {
		super();
		this.countryDao = countryDao;
		this.cityDao = cityDao;
		this.countryDTOMapper = countryDTOMapper;
		this.cityDTOMapper=cityDTOMapper;


	}


	@Override
	public CountryDTO addCountry(AddCountryRequest addCountryRequest, CountryDTO countryDTO) {
		Optional<Country> existingCountry = countryDao.findCountryByCountryName(addCountryRequest.countryName());
		if (existingCountry.isEmpty()) {
			Country newCountry = new Country();
			newCountry.setCountryName(addCountryRequest.countryName());
			countryDao.save(newCountry);

			CountryDTO newCountryDTO = new CountryDTO();
			newCountryDTO.setCountryName(newCountry.getCountryName());
			newCountryDTO.setCountryId(newCountry.getCountryId());
		} else {
			throw new RuntimeException("This country already exists.");
		}

		return countryDTO;
	}

	@Override
	public void updateCountryByName(String countryName, CountryUpdateRequest updateRequest, CountryDTO countryDTO) {
		Country country = countryDao.findCountryByCountryName(countryName)
				.orElseThrow(() -> new RuntimeException("Country with this name is not found")
				);

		if (updateRequest.countryName() != null && !updateRequest.countryName().equals(country.getCountryName())) {

			country.setCountryName(updateRequest.countryName());
			country = countryDao.save(country);

			CountryDTO updatedCountryDTO = new CountryDTO();
			updatedCountryDTO.setCountryId(country.getCountryId());
			updatedCountryDTO.setCountryName(country.getCountryName());


		} else if (updateRequest.countryName().equals(country.getCountryName())) {
			throw new RuntimeException("This country name update is same as before.");

		} else {
			throw new RuntimeException("ERROR");
		}

	}

	@Override
	public void updateCountryById(int countryId, CountryUpdateRequest updateRequest, CountryDTO countryDTO) {
		Country country = countryDao.findCountryByCountryId(countryId)
				.orElseThrow(() -> new RuntimeException("Country with this id is not found")
				);

		if (!updateRequest.countryName().equals(country.getCountryName())) {
			country.setCountryName(updateRequest.countryName());
			country = countryDao.save(country);

			CountryDTO updatedCountryDTO = new CountryDTO();
			updatedCountryDTO.setCountryName(country.getCountryName());
			updatedCountryDTO.setCountryId(country.getCountryId());

		} else {
			throw new RuntimeException("This country name update is same as before.");
		}

	}


	@Override
	public void deleteCountryByName(DeleteCountryByNameRequest deleteCountryByNameRequest) {
		Country existingCountry=countryDao.findCountryByCountryName(deleteCountryByNameRequest.countryName())
						.orElseThrow(()->new RuntimeException("This country you are trying to delete does not exist by name."));

		if(existingCountry!=null) {
			countryDao.deleteCountryByCountryName(deleteCountryByNameRequest.countryName());
		}

	}

	@Override
	public void deleteCountryById(DeleteCountryByIdRequest deleteCountryByIdRequest) {
		Country existingCountry=countryDao.findCountryByCountryId(deleteCountryByIdRequest.countryId())
						.orElseThrow(()->new EntityNotFoundException("This country you are trying to delete does not exist by name."));

		if(existingCountry!=null) {
			countryDao.deleteCountryByCountryId(deleteCountryByIdRequest.countryId());
		}}


	@Override
	public CityDTO addCityToCountryByName(String countryName,AddCityToCountryRequest addCityToCountryRequest) {
		Country country = countryDao.findCountryByCountryName(countryName)
				.orElseThrow(() -> new RuntimeException("This country by name is not found"));


		City city = new City();
		city.setCityName(addCityToCountryRequest.cityName());
		city.setCountry(country);

		city = cityDao.save(city);

		CityDTO addedCityDTO = new CityDTO();
		addedCityDTO.setCityName(city.getCityName());
		//addedCityDTO.setCountry(country);

		return addedCityDTO;


	}

	@Override
	public CityDTO addCityToCountryById(int countryId,AddCityToCountryRequest addCityToCountryRequest) {
		Country country = countryDao.findCountryByCountryId(countryId)
				.orElseThrow(() -> new EntityNotFoundException("This country is not found by id."));

		City city = new City();
		city.setCityName(addCityToCountryRequest.cityName());
		city.setCountry(country);

		city = cityDao.save(city);

		CityDTO addedCityDTO = new CityDTO();
		addedCityDTO.setCityName(city.getCityName());
		//addedCityDTO.setCountry(country);

		return addedCityDTO;


	}


	@Override
	public void  updateCitiesInCountryById(int countryId, String cityName, UpdateCityInCountryRequest updateCityInCountryRequest) {
		Country country = countryDao.findCountryByCountryId(countryId)
				.orElseThrow(() -> new RuntimeException("This country by Id does not exist."));

		City cityToUpdate = cityDao.findCityByCityName(cityName);

		if (!cityToUpdate.getCountry().equals(country)) {
			throw new IllegalStateException("City is not in the specified country");
		}
		cityToUpdate.setCityName(updateCityInCountryRequest.cityName());
		cityDao.save(cityToUpdate);
		CityDTO updatedCityDTO = new CityDTO();
		updatedCityDTO.setCityName(cityToUpdate.getCityName());
		//updatedCityDTO.setCountry(country);




	}

	@Override
	public void updateCitiesInCountryByName(String countryName, String cityName, UpdateCityInCountryRequest updateCityInCountryRequest) {
		Country country = countryDao.findCountryByCountryName(countryName)
				.orElseThrow(() -> new RuntimeException("This country by Id does not exist."));

		City cityToUpdate = cityDao.findCityByCityName(cityName);


		if (!cityToUpdate.getCountry().equals(country)) {
			throw new IllegalStateException("City is not in the specified country");
		}
		cityToUpdate.setCityName(updateCityInCountryRequest.cityName());
		cityDao.save(cityToUpdate);
		CityDTO updatedCityDTO = new CityDTO();
		updatedCityDTO.setCityName(cityToUpdate.getCityName());
		//updatedCityDTO.setCountry(country);



	}

	@Override
	public void deleteCityInCountryByName(String countryName, DeleteCityInCountryRequest deleteCityInCountryRequest) {
		Country country = countryDao.findCountryByCountryName(countryName)
				.orElseThrow(() -> new RuntimeException("This country does not exist."));
		City existingCity = cityDao.findCityByCityIdOrCityName(deleteCityInCountryRequest.cityId(), deleteCityInCountryRequest.cityName())
				.orElseThrow(() -> new RuntimeException("This city does not exist."));

		if(!existingCity.getCountry().equals(country)){
			throw new IllegalStateException("The city you are trying to delete is not in the specified country.");
		}
		cityDao.deleteCityByCityNameOrCityId(deleteCityInCountryRequest.cityName(), deleteCityInCountryRequest.cityId());


	}

	@Override
	public void deleteCityInCountryById(int countryId, DeleteCityInCountryRequest deleteCityInCountryRequest) {
		Country country = countryDao.findCountryByCountryId(countryId)
				.orElseThrow(() -> new RuntimeException("This country does not exist."));
		City existingCity = cityDao.findCityByCityIdOrCityName(deleteCityInCountryRequest.cityId(), deleteCityInCountryRequest.cityName())
				.orElseThrow(() -> new RuntimeException("This city does not exist."));

		if(!existingCity.getCountry().equals(country))
		{
			throw new IllegalStateException("The city you are trying to delete is not in the specified country.");
		}
			cityDao.deleteCityByCityNameOrCityId(deleteCityInCountryRequest.cityName(), deleteCityInCountryRequest.cityId());
	}


	@Override
	public List<CountryDTO> getAllCountries() {

		return countryDao.findAll()
				.stream()
				.map(countryDTOMapper)
				.collect(Collectors.toList());

	}


	@Override
	public Optional<Country> getCountryById(int countryId) {
		return countryDao.findCountryByCountryId(countryId);


	}

	@Override
	public Optional<Country> getCountryByName(String countryName) {

		return countryDao.findCountryByCountryName(countryName);
	}

	@Override
	public List<City> getCitiesInCountryByName(String countryName) {
		Optional<Country> existingCountry = countryDao.findCountryByCountryName(countryName);


		if (existingCountry.isEmpty()) {
			throw new EntityNotFoundException("This country is not found.");

		} else {
			return cityDao.findCitiesByCountryCountryName(countryName);
		}}
	@Override
	public List<City> getCitiesInCountryById (int countryId){
		Optional<Country> existingCountry = countryDao.findCountryByCountryId(countryId);

		if (existingCountry.isEmpty()) {throw new EntityNotFoundException("This country is not found.");}
		else {
			return cityDao.findCitiesByCountryCountryId(countryId);}}}










