package ashina.carrental.address.api.controllers;
import ashina.carrental.address.business.abstracts.BuildingNumberService;
import ashina.carrental.address.entities.concretes.BuildingNumber;
import ashina.carrental.address.entities.concretes.dtos.requests.buildingnumber.AddBuildingNumberRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/BuildingNumber")
public class BuildingNumberController {
    private final BuildingNumberService buildingNumberService;
    public BuildingNumberController(BuildingNumberService buildingNumberService){
        super();
        this.buildingNumberService=buildingNumberService;
    }
    @PostMapping("/AddBuildingNumber/{streetName}")
    public BuildingNumber addBuildingNumber(@PathVariable String streetName, @RequestBody AddBuildingNumberRequest addBuildingNumberRequest){return buildingNumberService.addBuildingNumber(streetName,addBuildingNumberRequest);}
    @PutMapping("/UpdateBuildingNumberByNo/{buildingNo}")
    public void updateBuildingNumberByNo(@PathVariable int buildingNo,@RequestBody AddBuildingNumberRequest addBuildingNumberRequest){buildingNumberService.updateBuildingNumberByNo(buildingNo,addBuildingNumberRequest);}
    @PutMapping("/UpdateBuildingNumberById/{buildingNumberId}")
    public void updateBuildingNumberById(@PathVariable int buildingNumberId,@RequestBody AddBuildingNumberRequest addBuildingNumberRequest){buildingNumberService.updateBuildingNumberById(buildingNumberId,addBuildingNumberRequest);}
    @DeleteMapping("/DeleteBuildingNumberByName")
    public void deleteBuildingNumberByNo(int buildingNo){buildingNumberService.deleteBuildingNumberByNo(buildingNo);}
    @DeleteMapping("/DeleteBuildingNumberById")
    public void deleteBuildingNumberById(int buildingNumberId){buildingNumberService.deleteBuildingNumberById(buildingNumberId);}
    @GetMapping("/GetAllBuildingNumbers")
    public List<BuildingNumber> getAllBuildingNumbers(){return buildingNumberService.getAllBuildingNumbers();}
    @GetMapping("/GetBuildingNumberByNo/{buildingNo}")
    public BuildingNumber getBuildingNumberByNo(@PathVariable int buildingNo){return buildingNumberService.getBuildingNumberByNo(buildingNo);}
    @GetMapping("/GetBuildingNumberById/{buildingNumberId}")
    public BuildingNumber getBuildingNumberById(@PathVariable int buildingNumberId){return buildingNumberService.getBuildingNumberById(buildingNumberId);}
}
