package ashina.carrental.car.api.controller;


import ashina.carrental.car.business.abstracts.CarModelService;
import ashina.carrental.car.entities.Car;
import ashina.carrental.car.entities.CarModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carModels")
public class CarModelController {
    private final CarModelService carModelService;

    @PostMapping("/addCarModel/{brandId}")
    public ResponseEntity<CarModel> addCarModel(@PathVariable int brandId,@RequestParam String modelName){

        CarModel addedCarModel=carModelService.addCarModel(brandId,modelName);
        return ResponseEntity.ok(addedCarModel);

    }
    @PutMapping("/updateCarModelByName/{modelName}")
    public ResponseEntity<CarModel> updateCarModelByName(@PathVariable String modelName,@RequestParam String updateModelName,@RequestParam String brandName)
    {
        CarModel updatedCarModel=carModelService.updateCarModelByName(modelName,updateModelName,brandName);
        return  ResponseEntity.ok(updatedCarModel);

    }
    @PutMapping("/updateCarModelById/{id}")
    public ResponseEntity<CarModel> updateCarModelById(@PathVariable int id,@RequestParam String modelName,@RequestParam int brandId)
    {
        CarModel updatedCarModel=carModelService.updateCarModelById(id,modelName,brandId);
        return  ResponseEntity.ok(updatedCarModel);

    }
    @DeleteMapping("/deleteCarModelByName")
    public void deleteCarModelByName(@RequestParam String modelName){
        carModelService.deleteCarModelByName(modelName);

    }
    @DeleteMapping("/deleteCarModelById")
    public void deleteCarModelById(@RequestParam int id){
        carModelService.deleteCarModelById(id);

    }
    @PostMapping("/addCarModelToCarById/{carId}")
    public ResponseEntity<Car> addCarModelToCarById(@PathVariable int carId,@RequestParam int modelId){
        Car updatedCar=carModelService.addCarModelToCarById(carId,modelId);
        return ResponseEntity.ok(updatedCar);
    }
    @PostMapping("/addCarModelToCarByName/{carId}")
    public ResponseEntity<Car> addCarModelToCarByName(@PathVariable int carId,@RequestParam String modelName){
        Car updatedCar=carModelService.addCarModelToCarByName(carId,modelName);
        return ResponseEntity.ok(updatedCar);
    }
    @PutMapping("/updateCarModelInCarByName/{id}")
    public ResponseEntity<Car> updateCarModelInCarByName(@PathVariable int id,@RequestParam String modelName){
        Car updatedCar=carModelService.updateCarModelInCarByName(id,modelName);
        return  ResponseEntity.ok(updatedCar);
    }
    @PutMapping("/updateCarModelInCarById/{id}")
    public ResponseEntity<Car> updateCarModelInCarById(@PathVariable int id,@RequestParam int modelId){
        Car updatedCar=carModelService.updateCarModelInCarById(id,modelId);
        return  ResponseEntity.ok(updatedCar);
    }
    @DeleteMapping("/deleteCarModelInCar")
    public void deleteCarModelInCar(@RequestParam int id)
    {
        carModelService.deleteCarModelInCar(id);
    }
    @GetMapping("/getAllCarModels")
    public List<String> getAllCarModels(){
        return carModelService.getAllCarModels();
    }
    @GetMapping("/getAllCarModelsInBrand/{id}")
    public List<String> getAllCarModelsInBrand(@PathVariable int id){
        return carModelService.getAllCarModelsInBrand(id);
    }

}
