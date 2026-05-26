package ashina.carrental.car.api.controller;


import ashina.carrental.car.business.abstracts.PriceService;
import ashina.carrental.car.entities.Price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    private final PriceService priceService;
    @Autowired
    public PriceController(PriceService priceService){
        super();
        this.priceService=priceService;
    }


    @PostMapping( "/addPriceToCar/{id}")
    public ResponseEntity<Price> addPriceToCar(@PathVariable("id") int id, @RequestParam("price") int price){
        Price addedPrice=priceService.addPriceToCar(id, price);
        return ResponseEntity.ok(addedPrice);
    }
    @PutMapping("/updatePriceInCar/{id}")
    public ResponseEntity<Price> updatePriceInCar(@PathVariable int id,@RequestParam("price") int price){
        Price updatedPrice=priceService.updatePriceInCar(id,price);
        return ResponseEntity.ok(updatedPrice);
    }
    @DeleteMapping("/deletePriceInCar/{id}")
    public void deletePriceInCar(@PathVariable int id){
        priceService.deletePriceInCar(id);
    }

}
