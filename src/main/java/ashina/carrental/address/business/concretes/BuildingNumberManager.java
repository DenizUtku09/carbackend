package ashina.carrental.address.business.concretes;

import ashina.carrental.address.business.abstracts.BuildingNumberService;
import ashina.carrental.address.dataAccess.abstracts.BuildingNumberDao;
import ashina.carrental.address.dataAccess.abstracts.StreetDao;
import ashina.carrental.address.entities.concretes.BuildingNumber;
import ashina.carrental.address.entities.concretes.Street;
import ashina.carrental.address.entities.concretes.dtos.BuildingNumberDTO;
import ashina.carrental.address.entities.concretes.dtos.requests.buildingnumber.AddBuildingNumberRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class BuildingNumberManager implements BuildingNumberService {
    private final BuildingNumberDao buildingNumberDao;
    private final StreetDao streetDao;
    @Autowired
    public BuildingNumberManager(BuildingNumberDao buildingNumberDao,StreetDao streetDao){
        super();
        this.buildingNumberDao=buildingNumberDao;
        this.streetDao=streetDao;
    }

    @Override
    public BuildingNumber addBuildingNumber(String streetName, AddBuildingNumberRequest addBuildingNumberRequest) {
        Street existingStreet=streetDao.findStreetByStreetName(streetName);
        if(existingStreet==null){throw new EntityNotFoundException("This street is not found.");}
        else{
            BuildingNumber addedBuildingNumber=new BuildingNumber();
            addedBuildingNumber.setBuildingNo(addBuildingNumberRequest.buildingNo());
            addedBuildingNumber.setStreet(existingStreet);
            buildingNumberDao.save(addedBuildingNumber);

            BuildingNumberDTO addedBuildingNumberDTO=new BuildingNumberDTO();
            addedBuildingNumberDTO.setBuildingNo(addedBuildingNumber.getBuildingNo());
            addedBuildingNumberDTO.setBuildingNumberId(addedBuildingNumber.getBuildingNumberId());
            addedBuildingNumber.setStreet(existingStreet);
            return  buildingNumberDao.save(addedBuildingNumber);}}

    @Override
    public void updateBuildingNumberByNo(int buildingNo, AddBuildingNumberRequest addBuildingNumberRequest) {
        BuildingNumber existingBuildingNumber=buildingNumberDao.findByBuildingNo(buildingNo);
        if(existingBuildingNumber==null){throw new EntityNotFoundException("This building number does not exist.");}
        else{existingBuildingNumber.setBuildingNo(addBuildingNumberRequest.buildingNo());
        buildingNumberDao.save(existingBuildingNumber);}}

    @Override
    public void updateBuildingNumberById(int buildingNumberId, AddBuildingNumberRequest addBuildingNumberRequest) {
        BuildingNumber existingBuildingNumber=buildingNumberDao.findByBuildingNumberId(buildingNumberId);
        if(existingBuildingNumber==null){throw new EntityNotFoundException("This building number does not exist.");}
        else{existingBuildingNumber.setBuildingNo(addBuildingNumberRequest.buildingNo());
        buildingNumberDao.save(existingBuildingNumber);}}

    @Override
    public void deleteBuildingNumberByNo(int buildingNo) {
        BuildingNumber existingBuildingNumber=buildingNumberDao.findByBuildingNo(buildingNo);
        if(existingBuildingNumber!=null){buildingNumberDao.delete(existingBuildingNumber);}
        else{throw new EntityNotFoundException("This building number does not exist by number.");}



    }

    @Override
    public void deleteBuildingNumberById(int buildingNumberId) {
        BuildingNumber existingBuildingNumber=buildingNumberDao.findByBuildingNumberId(buildingNumberId);
        if(existingBuildingNumber!=null){buildingNumberDao.delete(existingBuildingNumber);}
        else{throw new EntityNotFoundException("This building number does not exist by number.");}

    }

    @Override
    public List<BuildingNumber> getAllBuildingNumbers() {
        return buildingNumberDao.findAll();
    }

    @Override
    public BuildingNumber getBuildingNumberByNo(int buildingNo) {
        boolean existingBuildingNumber=buildingNumberDao.existsByBuildingNo(buildingNo);
        if(existingBuildingNumber){return buildingNumberDao.findByBuildingNo(buildingNo);}
        else{throw new EntityNotFoundException("This building number does not exist.");}}

    @Override
    public BuildingNumber getBuildingNumberById(int buildingNumberId) {
        boolean existingBuildingNumber=buildingNumberDao.existsByBuildingNumberId(buildingNumberId);
        if(existingBuildingNumber){return buildingNumberDao.findByBuildingNumberId(buildingNumberId);}
        else{throw new EntityNotFoundException("This building number does not exist.");}}
    }


