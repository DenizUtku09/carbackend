package ashina.carrental.address.business.abstracts;
import ashina.carrental.address.entities.concretes.BuildingNumber;
import ashina.carrental.address.entities.concretes.Street;
import ashina.carrental.address.entities.concretes.dtos.BuildingNumberDTO;
import ashina.carrental.address.entities.concretes.dtos.StreetDTO;
import ashina.carrental.address.entities.concretes.dtos.requests.buildingnumber.AddBuildingNumberRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.buildingnumber.DeleteBuildingNumberByIdRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.buildingnumber.DeleteBuildingNumberByNameRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.AddStreetRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.DeleteStreetByIdRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.street.DeleteStreetByNameRequest;

import java.util.List;

public interface StreetService {
    StreetDTO addStreet(String cityName, AddStreetRequest addStreetRequest);
    void updateStreetByName(String streetName, AddStreetRequest addStreetRequest);
    void updateStreetById(int streetId,AddStreetRequest addStreetRequest);
    void deleteStreetByName(DeleteStreetByNameRequest deleteStreetByNameRequest);
    void deleteStreetById(DeleteStreetByIdRequest deleteStreetByIdRequest);
    BuildingNumberDTO addBuildingNumberToStreetByName(String streetName, AddBuildingNumberRequest addBuildingNumberRequest);
    BuildingNumberDTO addBuildingNumberToStreetById(int streetId,AddBuildingNumberRequest addBuildingNumberRequest);
    BuildingNumberDTO updateBuildingNumberInStreetByName(String streetName,int buildingNo,AddBuildingNumberRequest addBuildingNumberRequest);
    BuildingNumberDTO updateBuildingNumberInStreetById(int streetId,int buildingNumberId,AddBuildingNumberRequest addBuildingNumberRequest);
    void deleteBuildingNumberInStreetByName(String streetName, DeleteBuildingNumberByNameRequest deleteBuildingNumberByNameRequest);
    void deleteBuildingNumberInStreetById(int streetId, DeleteBuildingNumberByIdRequest deleteBuildingNumberByIdRequest);

    List<StreetDTO> getAllStreets();
    Street getStreetByName(String streetName);
    Street getStreetById(int streetId);
    List<BuildingNumber> getBuildingNumbersInStreetByName(String streetName);
    List<BuildingNumber> getBuildingNumbersInStreetById(int streetId);







}
