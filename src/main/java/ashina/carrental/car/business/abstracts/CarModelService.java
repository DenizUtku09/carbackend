package ashina.carrental.car.business.abstracts;

import ashina.carrental.car.entities.Car;
import ashina.carrental.car.entities.CarModel;

import java.util.List;

public interface CarModelService {

    CarModel addCarModel(int brandId,String modelName);
    CarModel updateCarModelByName(String modelName,String updateModelName,String brandName);
    CarModel updateCarModelById(int id,String modelName,int brandId);
    void deleteCarModelByName(String modelName);
    void deleteCarModelById(int id);

    Car addCarModelToCarByName(int id,String modelName);
    Car addCarModelToCarById(int carId,int modelId);

    Car updateCarModelInCarByName(int id,String modelName);
    Car updateCarModelInCarById(int id,int modelId);
    Car deleteCarModelInCar(int id);

    List<String> getAllCarModels();
    List<String> getAllCarModelsInBrand(int id);




}
