package ashina.carrental.car.api.controller;



import ashina.carrental.car.business.abstracts.ColorService;
import ashina.carrental.car.entities.Car;
import ashina.carrental.car.entities.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/colors")
public class ColorController {
    private final ColorService colorService;
    @Autowired
    public ColorController(ColorService colorService){
        super();
        this.colorService=colorService;
    }
    @PostMapping("/addColor")
    public ResponseEntity<Color> addColor(@RequestParam String colorName){
        Color addedColor=colorService.addColor(colorName);
        return ResponseEntity.ok(addedColor);
    }

    @PutMapping("/updateColor/{id}/{colorName}")
    public ResponseEntity<Color> updateColor(@PathVariable int id,@PathVariable String colorName,@RequestParam String updatedColorName){
        Color updatedColor=colorService.updateColor(id,colorName,updatedColorName);
        return ResponseEntity.ok(updatedColor);
    }
    @DeleteMapping("/deleteColor")
    public void deleteColor(@RequestParam int id,@RequestParam String colorName){
        colorService.deleteColor(id,colorName);
    }
    @PostMapping("/addColorToCar/{carId}")
    public ResponseEntity<Car> addColorToCar(@PathVariable int carId,@RequestParam int colorId,@RequestParam String colorName){
        Car updatedCar=colorService.addColorToCar(carId,colorId,colorName);
        return ResponseEntity.ok(updatedCar);
    }
    @PutMapping("/updateColorInCar/{carId}")
    public ResponseEntity<Car> updateColorInCar(@PathVariable int carId,@RequestParam int colorId,@RequestParam String colorName){
        Car updatedCar=colorService.addColorToCar(carId,colorId,colorName);
        return ResponseEntity.ok(updatedCar);
    }
    @DeleteMapping("/deleteColorInCar")
    public void deleteColorInCar(@RequestParam int carId){
        colorService.deleteColorInCar(carId);

    }

    @GetMapping("/getAllColors")
    public ResponseEntity<List<Color>> getAllColors(){
        return ResponseEntity.ok(colorService.getAllColors());
    }


}

