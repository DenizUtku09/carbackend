package ashina.carrental.address.business.concretes;
import ashina.carrental.address.business.abstracts.CityService;
import ashina.carrental.address.dataAccess.abstracts.CityDao;
import ashina.carrental.address.dataAccess.abstracts.CountryDao;
import ashina.carrental.address.dataAccess.abstracts.StreetDao;
import ashina.carrental.address.entities.concretes.City;
import ashina.carrental.address.entities.concretes.Country;
import ashina.carrental.address.entities.concretes.Street;
import ashina.carrental.address.entities.concretes.dtos.CityDTO;
import ashina.carrental.address.entities.concretes.dtos.StreetDTO;
import ashina.carrental.address.entities.concretes.dtos.mappers.CityDTOMapper;
import ashina.carrental.address.entities.concretes.dtos.requests.city.AddCityRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.city.DeleteCityByIdRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.city.DeleteCityByNameRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.city.UpdateCityRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.AddStreetRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.DeleteStreetByIdRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.DeleteStreetByNameRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityManager implements CityService {
    private final CountryDao countryDao;
    private final CityDao cityDao;
    private final CityDTOMapper cityDTOMapper;
    private final StreetDao streetDao;
    @Autowired
    public CityManager(CityDao cityDao, StreetDao streetDao,CountryDao countryDao,CityDTOMapper cityDTOMapper){
        super();
        this.cityDao=cityDao;
        this.countryDao=countryDao;
        this.streetDao = streetDao;
        this.cityDTOMapper=cityDTOMapper;
    }
    @Override
    public CityDTO addCity(String countryName,AddCityRequest addCityRequest) {
        Country existingCountry = countryDao.findCountryByCountryName(countryName)
                .orElseThrow(()->new EntityNotFoundException("This country is not found."));
        boolean existingCity = cityDao.existsByCityName(addCityRequest.cityName());
        if(existingCity) {
            throw new IllegalStateException("This city you are trying to add already exists.");
        }
        else{
            City addedCity = new City();
            addedCity.setCityName(addCityRequest.cityName());
            addedCity.setCountry(existingCountry);
            cityDao.save(addedCity);

            CityDTO addedCityDTO = new CityDTO();
            addedCityDTO.setCityName(addedCity.getCityName());
            addedCityDTO.setCityId(addedCity.getCityId());
            //addedCityDTO.setCountry(addedCity.getCountry());

        }
        return null;
    }
    @Override
    public void updateCityByName(String cityName, UpdateCityRequest updateCityRequest) {
        City existingCity=cityDao.findCityByCityName(cityName);
        if(existingCity.getCityName().isEmpty()){throw new RuntimeException("This city does not exist.");}
        existingCity.setCityName(updateCityRequest.cityName());
        cityDao.save(existingCity);
        }
    @Override
    public void updateCityById(int cityId,UpdateCityRequest updateCityRequest){
        City existingCity=cityDao.findCityByCityId(cityId);
        if(existingCity.getCityName().equals(updateCityRequest.cityName())){
            throw new EntityNotFoundException("This city name is same as before.");
        }
        existingCity.setCityName(updateCityRequest.cityName());
        cityDao.save(existingCity);
    }



    @Override
    public void deleteCityByName(DeleteCityByNameRequest deleteCityByNameRequest){
        City existingCity=cityDao.findCityByCityName(deleteCityByNameRequest.cityName());
        if(existingCity.getCityName().equals(deleteCityByNameRequest.cityName())){
            cityDao.delete(existingCity);
        }
        else{
            throw new RuntimeException("This city does not exist.");
        }}
    public void deleteCityById(DeleteCityByIdRequest deleteCityByIdRequest){
        cityDao.deleteById(deleteCityByIdRequest.cityId());
    }

    @Override
    public StreetDTO addStreetToCityById(int cityId,AddStreetRequest addStreetRequest) {
        City existingCity = cityDao.findCityByCityId(cityId);

        if(existingCity==null){
            throw new RuntimeException("This city is not found by ID.");
        }
        if(!streetDao.existsByStreetName(addStreetRequest.streetName())){
            Street addedStreet=new Street();
            addedStreet.setStreetName(addStreetRequest.streetName());
            addedStreet.setCity(existingCity);
            streetDao.save(addedStreet);

            StreetDTO addedStreetDTO=new StreetDTO();
            addedStreetDTO.setStreetName(addedStreet.getStreetName());
            addedStreetDTO.setStreetId(addedStreet.getStreetId());
            addedStreetDTO.setCity(addedStreet.getCity());
            return addedStreetDTO;
        }
        else{
            throw new RuntimeException("This street already exists.");
        }
    }

    @Override
    public StreetDTO addStreetToCityByName(String cityName,AddStreetRequest addStreetRequest){
        City existingCity = cityDao.findCityByCityName(cityName);
        if(existingCity==null){
            throw new RuntimeException("This city is not found by name");
        }
        if(!streetDao.existsByStreetName(addStreetRequest.streetName())){
            Street addedStreet=new Street();
            addedStreet.setStreetName(addStreetRequest.streetName());
            addedStreet.setCity(existingCity);
            streetDao.save(addedStreet);

            StreetDTO addedStreetDTO=new StreetDTO();
            addedStreetDTO.setStreetName(addedStreet.getStreetName());
            addedStreetDTO.setStreetId(addedStreet.getStreetId());
            addedStreetDTO.setCity(addedStreet.getCity());
            return addedStreetDTO;
        }
        else{
            throw new RuntimeException("This street already exists.");
        }


    }
    public StreetDTO updateStreetInCityByName(String cityName,String streetName,AddStreetRequest addStreetRequest){
        City existingCity = cityDao.findCityByCityName(cityName);
        Street existingStreet = streetDao.findByStreetName(streetName);
        if(existingCity==null){
            throw new RuntimeException("This city is not found by name.");
        }
        if(existingStreet!=null){
            if(existingStreet.getStreetName().equals(addStreetRequest.streetName())){throw new IllegalStateException("The street name is same as before.");}
            else{existingStreet.setStreetName(addStreetRequest.streetName());
                streetDao.save(existingStreet);
            }}
        if(existingStreet==null){
            throw new RuntimeException("This street is not found by name.");
        }
        return null;}


    public StreetDTO updateStreetInCityById(int cityId,int streetId,AddStreetRequest addStreetRequest){
        City existingCity = cityDao.findCityByCityId(cityId);
        Street existingStreet = streetDao.findByStreetId(streetId);
        if(existingCity==null){
            throw new RuntimeException("This city is not found by ID.");
        }
        if(existingStreet!=null){
            if(existingStreet.getStreetName().equals(addStreetRequest.streetName())){throw new IllegalStateException("The street name is same as before.");}
            else{existingStreet.setStreetName(addStreetRequest.streetName());
                streetDao.save(existingStreet);
            }}
        if(existingStreet==null){
            throw new RuntimeException("This street is not found by ID.");
        }
        return null;}
    public void deleteStreetInCityByName(String cityName, DeleteStreetByNameRequest deleteStreetByNameRequest){
        City existingCity=cityDao.findCityByCityName(cityName);
        Street existingStreet=streetDao.findByStreetName(deleteStreetByNameRequest.streetName());
        if(existingCity==null){
            throw new EntityNotFoundException("This city by name has not been found");
        }
        else if(existingStreet==null){
            throw new EntityNotFoundException("This street you are trying to delete does not exist by name.");
        }
        else{
            streetDao.delete(existingStreet);
        }}
    public void deleteStreetInCityById(int cityId, DeleteStreetByIdRequest deleteStreetByIdRequest){
        City existingCity=cityDao.findCityByCityId(cityId);
        Street existingStreet=streetDao.findStreetByStreetId(deleteStreetByIdRequest.streetId());
        if(existingCity==null){
            throw new EntityNotFoundException("This city by name has not been found");
        }
        else if(existingStreet==null){
            throw new EntityNotFoundException("This street you are trying to delete does not exist by name.");
        }
        else{
            streetDao.delete(existingStreet);
        }

    }








    @Override
    public City getCityByCityName(String cityName) {
        return cityDao.findCityByCityName(cityName);


    }
    public City getCityById(int cityId){
        return cityDao.findCityByCityId(cityId);


    }

    @Override
    public List<Street> getStreetsInCityByName(String cityName) {
        return streetDao.findStreetsByCityCityName(cityName);


    }

    @Override
    public List<Street> getStreetsInCityById(int cityId) {
        return streetDao.findStreetsByCityCityId(cityId);

    }

    public List<CityDTO> getAllCities(){
        return cityDao.findAll()
                .stream()
                .map(cityDTOMapper)
                .collect(Collectors.toList());

    }
}
