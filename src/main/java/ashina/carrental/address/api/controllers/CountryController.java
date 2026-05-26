package ashina.carrental.address.api.controllers;

import java.util.List;
import java.util.Optional;


import ashina.carrental.address.entities.concretes.Country;
import ashina.carrental.address.entities.concretes.dtos.CityDTO;
import ashina.carrental.address.entities.concretes.dtos.CountryDTO;
import ashina.carrental.address.entities.concretes.dtos.requests.country.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ashina.carrental.address.business.abstracts.CountryService;
import ashina.carrental.address.entities.concretes.City;



@RestController
@RequestMapping("/api/Country")
public class CountryController {
	private final CountryService countryService;
	@Autowired
	public CountryController(CountryService countryService) {
		super();
		this.countryService=countryService;
	}
	@GetMapping("/getAll")
	public List<CountryDTO>  getAllCountries() {return countryService.getAllCountries();}
	@GetMapping("/getCountryByName/{countryName}")
	public Optional<Country> getCountryByName(@PathVariable String countryName){return countryService.getCountryByName(countryName);}
	@GetMapping("/getCountryById/{countryId}")
	public Optional<Country> getCountryById(@PathVariable int countryId){return countryService.getCountryById(countryId);}
	@GetMapping("/GetCitiesInCountryByName/{countryName}")
	@ResponseBody
	public List<City> getCitiesInCountryByName(@PathVariable String countryName){return countryService.getCitiesInCountryByName(countryName);}
	@GetMapping("/GetCitiesInCountryById/{countryId}")
	@ResponseBody
	public List<City> getCitiesInCountryById(@PathVariable int countryId){return countryService.getCitiesInCountryById(countryId);}
	@PostMapping("/AddCountry")
	public ResponseEntity<CountryDTO> addCountry(@RequestBody AddCountryRequest addCountryRequest,CountryDTO countryDTO) {CountryDTO addedCountry=countryService.addCountry(addCountryRequest,countryDTO);return ResponseEntity.ok(addedCountry);}
	@PostMapping("/AddCitiesToCountryById/{countryId}")
	public ResponseEntity<CityDTO> addCityToCountryById(@PathVariable int countryId,@RequestBody AddCityToCountryRequest addCityToCountryRequest){CityDTO addedCity=countryService.addCityToCountryById(countryId,addCityToCountryRequest);return ResponseEntity.ok(addedCity);}
	@PostMapping("/AddCitiesToCountryByName/{countryName}")
	public ResponseEntity<CityDTO> addCityToCountryByName(@PathVariable String countryName,@RequestBody AddCityToCountryRequest addCityToCountryRequest){CityDTO addedCity=countryService.addCityToCountryByName(countryName,addCityToCountryRequest);return ResponseEntity.ok(addedCity);}
	@PutMapping("/UpdateCountryByName/{countryName}")
	@ResponseBody
	public void updateCountryByName(@PathVariable String countryName, @RequestBody CountryUpdateRequest updateRequest, CountryDTO countryDTO){countryService.updateCountryByName(countryName, updateRequest,countryDTO);}
	@PutMapping("/updateCountryById/{countryId}")
	public void updateCountryById(@PathVariable int countryId,@RequestBody CountryUpdateRequest updateRequest,CountryDTO countryDTO){countryService.updateCountryById(countryId,updateRequest,countryDTO);}
    @PutMapping("/UpdateCitiesInCountryById/{countryId}/city/{cityName}")
	public void updateCitiesInCountryById(@PathVariable int countryId, @PathVariable String cityName, @RequestBody UpdateCityInCountryRequest updateCityInCountryRequest) {countryService.updateCitiesInCountryById(countryId,cityName,updateCityInCountryRequest);}
	@PutMapping("/UpdateCitiesInCountryByName/{countryName}/city/{cityName}")
	public void updateCitiesInCountryByName (@PathVariable String countryName,@PathVariable String cityName, @RequestBody UpdateCityInCountryRequest updateCityInCountryRequest){countryService.updateCitiesInCountryByName(countryName,cityName,updateCityInCountryRequest);}
    @DeleteMapping("/DeleteCountryById")
	public void deleteCountryById(@RequestBody DeleteCountryByIdRequest deleteCountryByIdRequest){countryService.deleteCountryById(deleteCountryByIdRequest);}
	@DeleteMapping("/DeleteCountryByName")
	public void DeleteCountryByName(@RequestBody DeleteCountryByNameRequest deleteCountryByNameRequest){countryService.deleteCountryByName(deleteCountryByNameRequest);}
	@DeleteMapping("/DeleteCityInCountryByName/{countryName}")
	public void DeleteCityInCountryByName(@PathVariable String countryName,@RequestBody DeleteCityInCountryRequest deleteCityInCountryRequest){countryService.deleteCityInCountryByName(countryName,deleteCityInCountryRequest);}
	@DeleteMapping("/DeleteCityInCountryById/{countryId}")
	public void DeleteCityInCountryById(@PathVariable int countryId,@RequestBody DeleteCityInCountryRequest deleteCityInCountryRequest){countryService.deleteCityInCountryById(countryId,deleteCityInCountryRequest);}}
