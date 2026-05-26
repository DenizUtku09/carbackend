package ashina.carrental.address.api.controllers;

import ashina.carrental.address.business.abstracts.StreetService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Street")
public class StreetController {

    private final StreetService streetService;
    @Autowired
    public StreetController(StreetService streetService){
        super();
        this.streetService=streetService;
    }
    @PostMapping("/AddStreet/{cityName}")
    public ResponseEntity<StreetDTO> AddStreet(@PathVariable String cityName,@RequestBody AddStreetRequest addStreetRequest){return ResponseEntity.ok(streetService.addStreet(cityName,addStreetRequest));}
    @PutMapping("/UpdateStreetByName/{streetName}")
    public void updateStreetByName(@PathVariable String streetName, @RequestBody AddStreetRequest addStreetRequest){streetService.updateStreetByName(streetName,addStreetRequest);}
    @PutMapping("/UpdateStreetById/{streetId}")
    public void updateStreetById(@PathVariable int streetId,@RequestBody AddStreetRequest addStreetRequest){streetService.updateStreetById(streetId,addStreetRequest);}
    @DeleteMapping("/DeleteStreetById")
    public void deleteStreetById(@RequestBody DeleteStreetByIdRequest deleteStreetByIdRequest){streetService.deleteStreetById(deleteStreetByIdRequest);}
    @DeleteMapping("/DeleteStreetByName")
    public void deleteStreetByName(@RequestBody DeleteStreetByNameRequest deleteStreetByNameRequest){streetService.deleteStreetByName(deleteStreetByNameRequest);}
    @PostMapping("/AddBuildingNumberToStreetByName/{streetName}")
    public ResponseEntity<BuildingNumberDTO> addBuildingNumberToStreetByName(@PathVariable String streetName, @RequestBody AddBuildingNumberRequest addBuildingNumberRequest){return ResponseEntity.ok(streetService.addBuildingNumberToStreetByName(streetName,addBuildingNumberRequest));}
    @PostMapping("/AddBuildingNumberToStreetById/{streetId}")
    public ResponseEntity<BuildingNumberDTO> addBuildingNumberToStreetById(@PathVariable int streetId,@RequestBody AddBuildingNumberRequest addBuildingNumberRequest){return ResponseEntity.ok(streetService.addBuildingNumberToStreetById(streetId,addBuildingNumberRequest));}
    @PutMapping("/UpdateBuildingNumberInStreetByName/{streetName}/building-number/{buildingNo}")
    public ResponseEntity<BuildingNumberDTO> updateBuildingNumberInStreetByName(@PathVariable String streetName,@PathVariable int buildingNo,@RequestBody AddBuildingNumberRequest addBuildingNumberRequest){return ResponseEntity.ok(streetService.updateBuildingNumberInStreetByName(streetName,buildingNo,addBuildingNumberRequest));}
    @PutMapping("/UpdateBuildingNumberInStreetById/{streetId}/building-number/{buildingNumberId}")
    public ResponseEntity<BuildingNumberDTO> updateBuildingNumberInStreetByName(@PathVariable int streetId,@PathVariable int buildingNumberId,@RequestBody AddBuildingNumberRequest addBuildingNumberRequest){return ResponseEntity.ok(streetService.updateBuildingNumberInStreetById(streetId,buildingNumberId,addBuildingNumberRequest));}
    @DeleteMapping("/DeleteBuildingNumberInStreetByName/{streetName}")
    public void deleteBuildingNumberInStreetByName(@PathVariable String streetName, @RequestBody DeleteBuildingNumberByNameRequest deleteBuildingNumberByNameRequest){streetService.deleteBuildingNumberInStreetByName(streetName,deleteBuildingNumberByNameRequest);}
    @DeleteMapping("/DeleteBuildingNumberInStreetById/{streetId}")
    public void deleteBuildingNumberInStreetById(@PathVariable int streetId,@RequestBody DeleteBuildingNumberByIdRequest deleteBuildingNumberByIdRequest){streetService.deleteBuildingNumberInStreetById(streetId,deleteBuildingNumberByIdRequest);}
    @GetMapping("/GetAllStreets")
    public List<StreetDTO> getAllStreets(){return streetService.getAllStreets();}
    @GetMapping("/GetStreetByName/{streetName}")
    public Street getStreetByName(@PathVariable String streetName){return streetService.getStreetByName(streetName);}
    @GetMapping("/GetStreetById/{streetId}")
    public Street getStreetById(@PathVariable int streetId){return streetService.getStreetById(streetId);}
    @GetMapping("/GetBuildingNumbersInStreetByName/{streetName}")
    public List<BuildingNumber> getBuildingNumbersInStreetByName(@PathVariable String streetName){return streetService.getBuildingNumbersInStreetByName(streetName);}
    @GetMapping("/GetBuildingNumbersInStreetById/{streetId}")
    public List<BuildingNumber> getBuildingNumbersInStreetById(@PathVariable int streetId){return streetService.getBuildingNumbersInStreetById(streetId);}}
