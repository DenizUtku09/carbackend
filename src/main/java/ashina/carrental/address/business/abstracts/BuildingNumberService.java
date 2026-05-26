package ashina.carrental.address.business.abstracts;
import ashina.carrental.address.entities.concretes.BuildingNumber;
import ashina.carrental.address.entities.concretes.dtos.requests.buildingnumber.AddBuildingNumberRequest;
import java.util.List;
public interface BuildingNumberService {

    BuildingNumber addBuildingNumber(String streetName, AddBuildingNumberRequest addBuildingNumberRequest);
    void updateBuildingNumberByNo(int buildingNo,AddBuildingNumberRequest addBuildingNumberRequest);
    void updateBuildingNumberById(int buildingNumberId,AddBuildingNumberRequest addBuildingNumberRequest);
    void deleteBuildingNumberByNo(int buildingNo);
    void deleteBuildingNumberById(int buildingNumberId);
    List<BuildingNumber> getAllBuildingNumbers();
    BuildingNumber getBuildingNumberByNo(int buildingNo);
    BuildingNumber getBuildingNumberById(int buildingNumberId);


}
