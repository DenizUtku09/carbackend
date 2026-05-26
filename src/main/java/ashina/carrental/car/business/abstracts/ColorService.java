package ashina.carrental.car.business.abstracts;

import ashina.carrental.car.entities.Car;
import ashina.carrental.car.entities.Color;
import org.springframework.stereotype.Service;


public interface ColorService {

    Color addColor(String colorName);
    Color updateColor(int id,String colorName,String updatedColorName);
    void deleteColor(int id,String colorName);
    Car addColorToCar(int carId, int colorId, String colorName);
    Car updateColorInCar(int carId,int colorId,String colorName);
    void deleteColorInCar(int carId);
    java.util.List<Color> getAllColors();





}
