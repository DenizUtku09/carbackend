package ashina.carrental.address.business.concretes;
import ashina.carrental.address.business.abstracts.StreetService;
import ashina.carrental.address.dataAccess.abstracts.BuildingNumberDao;
import ashina.carrental.address.dataAccess.abstracts.CityDao;
import ashina.carrental.address.dataAccess.abstracts.StreetDao;
import ashina.carrental.address.entities.concretes.BuildingNumber;
import ashina.carrental.address.entities.concretes.City;
import ashina.carrental.address.entities.concretes.Street;
import ashina.carrental.address.entities.concretes.dtos.BuildingNumberDTO;
import ashina.carrental.address.entities.concretes.dtos.StreetDTO;
import ashina.carrental.address.entities.concretes.dtos.mappers.StreetDTOMapper;
import ashina.carrental.address.entities.concretes.dtos.requests.buildingnumber.AddBuildingNumberRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.buildingnumber.DeleteBuildingNumberByIdRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.buildingnumber.DeleteBuildingNumberByNameRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.AddStreetRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.DeleteStreetByIdRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.DeleteStreetByNameRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StreetManager implements StreetService {
    private final StreetDTOMapper streetDTOMapper;

    private final CityDao cityDao;
    private final BuildingNumberDao buildingNumberDao;
    private final StreetDao streetDao;
    @Autowired

    public StreetManager(StreetDao streetDao,CityDao cityDao,BuildingNumberDao buildingNumberDao,StreetDTOMapper streetDTOMapper){
        super();
        this.cityDao=cityDao;
        this.streetDao=streetDao;
        this.buildingNumberDao=buildingNumberDao;
        this.streetDTOMapper=streetDTOMapper;
    }

    @Override
    public StreetDTO addStreet(String cityName, AddStreetRequest addStreetRequest) {
        City existingCity=cityDao.findByCityName(cityName);
        boolean existingStreet=streetDao.existsByStreetName(addStreetRequest.streetName());
        if(!cityDao.existsByCityName(cityName)){
            throw new RuntimeException("This city does not exist.");
        }
        if(!existingStreet){
            Street addedStreet=new Street();
            addedStreet.setStreetName(addStreetRequest.streetName());
            addedStreet.setCity(existingCity);
            streetDao.save(addedStreet);
            StreetDTO addedStreetDTO=new StreetDTO();
            addedStreetDTO.setStreetId(addedStreet.getStreetId());
            addedStreetDTO.setStreetName(addedStreet.getStreetName());
            addedStreetDTO.setCity(addedStreet.getCity());
            return addedStreetDTO;
        }
        else{
            throw new IllegalStateException("This street you are trying to add already exists.");
        }}
    @Override
    public void updateStreetByName(String streetName, AddStreetRequest addStreetRequest) {
        Street existingStreet=streetDao.findByStreetName(streetName);
        if(addStreetRequest.streetName().equals(existingStreet.getStreetName())){
            throw new RuntimeException("This street name is same as before.");
        }

        else{existingStreet.setStreetName(addStreetRequest.streetName());}}
    @Override
    public void updateStreetById(int streetId, AddStreetRequest addStreetRequest) {
        Street existingStreet=streetDao.findByStreetId(streetId);
        if(addStreetRequest.streetName().equals(existingStreet.getStreetName())){
            throw new RuntimeException("This street name is same as before.");
        }
        else{existingStreet.setStreetName(addStreetRequest.streetName());}}
    @Override
    public void deleteStreetByName(DeleteStreetByNameRequest deleteStreetByNameRequest) {
        Street existingStreet=streetDao.findStreetByStreetName(deleteStreetByNameRequest.streetName());
        if(existingStreet!=null){
            streetDao.delete(existingStreet);
        }
        else{
            throw new RuntimeException("This street does not exist by name.");
        }
      }
    @Override
    public void deleteStreetById(DeleteStreetByIdRequest deleteStreetByIdRequest) {
        Street existingStreet=streetDao.findStreetByStreetId(deleteStreetByIdRequest.streetId());
        if(existingStreet!=null){
            streetDao.delete(existingStreet);
        }
        else{
            throw new RuntimeException("This street does not exist by ID.");
        }}

    @Override
    public BuildingNumberDTO addBuildingNumberToStreetByName(String streetName, AddBuildingNumberRequest addBuildingNumberRequest) {
        Street existingStreet=streetDao.findByStreetName(streetName);
        Optional<BuildingNumber> existingBuildingNumber=buildingNumberDao.findBuildingNumberByBuildingNo(addBuildingNumberRequest.buildingNo());

        if(existingStreet==null){throw new EntityNotFoundException("This street does not exist.");}
        if(existingBuildingNumber.isPresent()){throw new RuntimeException("This building number already exists.");}
        BuildingNumber addedBuildingNumber=new BuildingNumber();
        addedBuildingNumber.setBuildingNo(addBuildingNumberRequest.buildingNo());
        addedBuildingNumber.setStreet(existingStreet);
        buildingNumberDao.save(addedBuildingNumber);
        BuildingNumberDTO addedBuildingNumberDTO=new BuildingNumberDTO();
        addedBuildingNumberDTO.setBuildingNumberId(addedBuildingNumber.getBuildingNumberId());
        addedBuildingNumberDTO.setBuildingNo(addedBuildingNumber.getBuildingNo());
        addedBuildingNumber.setStreet(addedBuildingNumber.getStreet());
        return addedBuildingNumberDTO;}

    @Override
    public BuildingNumberDTO addBuildingNumberToStreetById(int streetId, AddBuildingNumberRequest addBuildingNumberRequest) {
        Street existingStreet=streetDao.findByStreetId(streetId);
        Optional<BuildingNumber> existingBuildingNumber=buildingNumberDao.findBuildingNumberByBuildingNo(addBuildingNumberRequest.buildingNo());
        if(existingStreet==null){throw new EntityNotFoundException("This street does not exist.");}
        if(existingBuildingNumber.isPresent()){throw new RuntimeException("This building number already exists.");}
        BuildingNumber addedBuildingNumber=new BuildingNumber();
        addedBuildingNumber.setBuildingNo(addBuildingNumberRequest.buildingNo());
        addedBuildingNumber.setStreet(existingStreet);
        buildingNumberDao.save(addedBuildingNumber);
        BuildingNumberDTO addedBuildingNumberDTO=new BuildingNumberDTO();
        addedBuildingNumberDTO.setBuildingNumberId(addedBuildingNumber.getBuildingNumberId());
        addedBuildingNumberDTO.setBuildingNo(addedBuildingNumber.getBuildingNo());
        addedBuildingNumber.setStreet(addedBuildingNumber.getStreet());
        return addedBuildingNumberDTO;
    }

    @Override
    public BuildingNumberDTO updateBuildingNumberInStreetByName(String streetName, int buildingNo, AddBuildingNumberRequest addBuildingNumberRequest) {
        Street existingStreet = streetDao.findByStreetName(streetName);
        BuildingNumber existingBuildingNumber = buildingNumberDao.findByBuildingNo(buildingNo);
        if (existingStreet == null) {
            throw new RuntimeException("This street does not exist by name");
        }
        if (existingBuildingNumber!=null) {
            existingBuildingNumber.setBuildingNo(addBuildingNumberRequest.buildingNo());

            BuildingNumberDTO updatedBuildingNumberDTO = new BuildingNumberDTO();
            updatedBuildingNumberDTO.setBuildingNo(existingBuildingNumber.getBuildingNo());
            return updatedBuildingNumberDTO;

        }
        else{
            throw new RuntimeException("This building number does not exist.");
        }}

    @Override
    public BuildingNumberDTO updateBuildingNumberInStreetById(int streetId, int buildingNumberId, AddBuildingNumberRequest addBuildingNumberRequest) {
        Street existingStreet = streetDao.findByStreetId(streetId);
        BuildingNumber existingBuildingNumber = buildingNumberDao.findByBuildingNumberId(buildingNumberId);
        if (existingStreet == null) {
            throw new RuntimeException("This street does not exist by name");
        }
        if (existingBuildingNumber!=null) {
            existingBuildingNumber.setBuildingNo(addBuildingNumberRequest.buildingNo());

            BuildingNumberDTO updatedBuildingNumberDTO = new BuildingNumberDTO();
            updatedBuildingNumberDTO.setBuildingNo(existingBuildingNumber.getBuildingNo());
            return updatedBuildingNumberDTO;

        }
        else{
            throw new RuntimeException("This building number does not exist.");
        }}

    @Override
    public void deleteBuildingNumberInStreetByName(String streetName, DeleteBuildingNumberByNameRequest deleteBuildingNumberByNameRequest) {
        boolean existingStreet=streetDao.existsByStreetName(streetName);
        BuildingNumber existingBuildingNumber=buildingNumberDao.findByBuildingNo(deleteBuildingNumberByNameRequest.buildingNo());

        if(!existingStreet){throw new EntityNotFoundException("This street is not found by name");}
        else if (existingBuildingNumber==null) {throw new EntityNotFoundException("This building number you are trying to delete does not exist by name.");}
        else{buildingNumberDao.delete(existingBuildingNumber);}



    }

    @Override
    public void deleteBuildingNumberInStreetById(int streetId, DeleteBuildingNumberByIdRequest deleteBuildingNumberByIdRequest) {
        boolean existingStreet=streetDao.existsByStreetId(streetId);
        BuildingNumber existingBuildingNumber=buildingNumberDao.findByBuildingNumberId(deleteBuildingNumberByIdRequest.buildingNumberId());
        if(!existingStreet){throw new EntityNotFoundException("This street is not found by ID");}
        else if(existingBuildingNumber==null){throw new EntityNotFoundException("This building number you are trying to delete does not exist by ID");}
        else{buildingNumberDao.delete(existingBuildingNumber);}
    }

    @Override
    public List<StreetDTO> getAllStreets() {
        return streetDao.findAll()
                .stream()
                .map(streetDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Street getStreetByName(String streetName) {
        Street existingStreet=streetDao.findByStreetName(streetName);
        if(existingStreet==null){throw new EntityNotFoundException("This street does not exist.");}
        else{return streetDao.findByStreetName(streetName);}
    }

    @Override
    public Street getStreetById(int streetId) {
        Street existingStreet=streetDao.findByStreetId(streetId);
        if(existingStreet==null){throw new EntityNotFoundException("This street does not exist");}
        else{return streetDao.findByStreetId(streetId);}
    }

    @Override
    public List<BuildingNumber> getBuildingNumbersInStreetByName(String streetName) {
        Street existingStreet=streetDao.findByStreetName(streetName);
        if(existingStreet==null){throw new EntityNotFoundException("This street does not exist.");}
        else{return buildingNumberDao.findBuildingNumbersByStreet(existingStreet);}
    }

    @Override
    public List<BuildingNumber> getBuildingNumbersInStreetById(int streetId) {
        Street existingStreet=streetDao.findByStreetId(streetId);
        if(existingStreet==null){throw new EntityNotFoundException("This street does not exist.");}
        else{return buildingNumberDao.findBuildingNumbersByStreet(existingStreet);}
    }
}

