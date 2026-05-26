package ashina.carrental.address.api.controllers;

import ashina.carrental.address.business.abstracts.CityService;

import ashina.carrental.address.entities.concretes.City;
import ashina.carrental.address.entities.concretes.Street;
import ashina.carrental.address.entities.concretes.dtos.CityDTO;
import ashina.carrental.address.entities.concretes.dtos.StreetDTO;
import ashina.carrental.address.entities.concretes.dtos.requests.city.AddCityRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.city.DeleteCityByIdRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.city.DeleteCityByNameRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.city.UpdateCityRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.AddStreetRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.DeleteStreetByIdRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.DeleteStreetByNameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/City")
public class CityController {
    private final CityService cityService;
    @Autowired
    public CityController(CityService cityService){
        super();
        this.cityService=cityService;
    }
    @PostMapping("/AddCity/{countryName}")
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO addCity(@PathVariable String countryName,@RequestBody AddCityRequest addCityRequest){return cityService.addCity(countryName,addCityRequest);
    }
    @PutMapping("/UpdateCityByName/{cityName}")
    public void updateCityByName(@PathVariable String cityName, UpdateCityRequest updateCityRequest){cityService.updateCityByName(cityName,updateCityRequest);}
    @PutMapping("/UpdateCityById/{cityId}")
    public void updateCityById(@PathVariable int cityId,UpdateCityRequest updateCityRequest) {cityService.updateCityById(cityId,updateCityRequest);}
    @DeleteMapping("/DeleteCityByName")
    public void deleteCityByName(@RequestBody DeleteCityByNameRequest deleteCityByNameRequest){cityService.deleteCityByName(deleteCityByNameRequest);}
    @DeleteMapping("/DeleteCityById")
    public void deleteCityById(@RequestBody DeleteCityByIdRequest deleteCityByIdRequest){cityService.deleteCityById(deleteCityByIdRequest);}
    @PostMapping("/AddStreetToCityByName/{cityName}")
    public StreetDTO addStreetToCityByName(@PathVariable String cityName,@RequestBody AddStreetRequest addStreetRequest){return cityService.addStreetToCityByName(cityName,addStreetRequest);}
    @PostMapping("/AddStreetToCityById/{cityId}")
    public StreetDTO addStreetToCityById(@PathVariable int cityId,@RequestBody AddStreetRequest addStreetRequest){return cityService.addStreetToCityById(cityId,addStreetRequest);}
    @PutMapping("/UpdateStreetInCityByName/{cityName}/street/{streetName}")
    public StreetDTO updateStreetInCityByName(@PathVariable String cityName,@PathVariable String streetName,AddStreetRequest addStreetRequest){return cityService.updateStreetInCityByName(cityName,streetName,addStreetRequest);}
    @PutMapping("/UpdateStreetInCityById/{cityId}/street/{streetId}")
    public StreetDTO updateStreetInCityById(@PathVariable int cityId,@PathVariable int streetId,AddStreetRequest addStreetRequest){return cityService.updateStreetInCityById(cityId,streetId,addStreetRequest);}
    @DeleteMapping("/DeleteStreetInCityByName/{cityName}")
    public void deleteStreetInCityByName(@PathVariable String cityName,@RequestBody DeleteStreetByNameRequest deleteStreetByNameRequest){cityService.deleteStreetInCityByName(cityName,deleteStreetByNameRequest);}
    @DeleteMapping("/DeleteStreetInCityById/{cityId}")
    public void deleteStreetInCityById(@PathVariable int cityId, DeleteStreetByIdRequest deleteStreetByIdRequest){cityService.deleteStreetInCityById(cityId,deleteStreetByIdRequest);}
    @GetMapping("/GetCityByName/{cityName}")
    public City getCityByCityName(@PathVariable String cityName){return cityService.getCityByCityName(cityName);}
    @GetMapping("/GetCityById/{cityId}")
    public City getCityById(@PathVariable int cityId){return cityService.getCityById(cityId);}
    @GetMapping("/GetStreetsInCityByName/{cityName}")
    public List<Street> getStreetsInCityByName(@PathVariable String cityName){return cityService.getStreetsInCityByName(cityName);}
    @GetMapping("/GetStreetsInCityById/{cityId}")
    public List<Street> getStreetsInCityById(@PathVariable int cityId){return cityService.getStreetsInCityById(cityId);}
    @GetMapping("/GetAllCities")
    public List<CityDTO> getAllCities(){return cityService.getAllCities();}
}
