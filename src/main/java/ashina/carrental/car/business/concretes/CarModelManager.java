package ashina.carrental.car.business.concretes;

import ashina.carrental.car.DataAccess.CarBrandDao;
import ashina.carrental.car.DataAccess.CarDao;
import ashina.carrental.car.DataAccess.CarModelDao;
import ashina.carrental.car.business.abstracts.CarModelService;
import ashina.carrental.car.entities.Car;
import ashina.carrental.car.entities.CarBrand;
import ashina.carrental.car.entities.CarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarModelManager implements CarModelService {
    private final CarModelDao carModelDao;
    private final CarBrandDao carBrandDao;
    private final CarDao carDao;
    @Autowired
    public CarModelManager(CarModelDao carModelDao,CarBrandDao carBrandDao,CarDao carDao){
        super();
        this.carDao=carDao;
        this.carBrandDao=carBrandDao;
        this.carModelDao=carModelDao;
    }

    @Override
    public CarModel addCarModel(int brandId, String modelName) {
        CarBrand existingCarBrand=carBrandDao.findCarBrandById(brandId)
                .orElseThrow(() -> new RuntimeException("This car brand does not exist."));
        Optional<CarModel> existingCarModel=carModelDao.findCarModelByModelName(modelName);
        if(existingCarModel.isPresent()){
            throw new RuntimeException("This car model already exists.");
        }
        CarModel addedCarModel=new CarModel();
        addedCarModel.setCarBrand(existingCarBrand);
        addedCarModel.setModelName(modelName);
        carModelDao.save(addedCarModel);
        return addedCarModel;
    }

    @Override
    public CarModel updateCarModelByName(String modelName, String updateModelName,String brandName) {
        CarModel existingCarModel=carModelDao.findCarModelByModelName(modelName)
                .orElseThrow(() -> new RuntimeException("This model does not exist by name."));
        CarBrand existingCarBrand=carBrandDao.findCarBrandByBrandName(brandName)
                .orElseThrow(() -> new RuntimeException("This brand does not exist by name."));
        if(existingCarBrand!=null){
            existingCarModel.setCarBrand(existingCarBrand);
        }
        existingCarModel.setModelName(updateModelName);
        carModelDao.save(existingCarModel);
        return existingCarModel;
    }

    @Override
    public CarModel updateCarModelById(int id, String modelName,int brandId) {
        CarModel existingCarModel=carModelDao.findCarModelById(id)
                .orElseThrow(() -> new RuntimeException("This model does not exist by name."));
        CarBrand existingCarBrand=carBrandDao.findCarBrandById(brandId)
                .orElseThrow(() -> new RuntimeException("This brand does not exist by name."));

        if(existingCarBrand!=null){
            existingCarModel.setCarBrand(existingCarBrand);
        }

        existingCarModel.setModelName(modelName);
        carModelDao.save(existingCarModel);
        return existingCarModel;
    }

    @Override
    public void deleteCarModelByName(String modelName) {
        CarModel existingCarModel=carModelDao.findCarModelByModelName(modelName)
                .orElseThrow(() -> new RuntimeException("This car model does not exist by name."));

        carModelDao.delete(existingCarModel);

    }

    @Override
    public void deleteCarModelById(int id) {
        CarModel existingCarModel=carModelDao.findCarModelById(id)
                .orElseThrow(() -> new RuntimeException("This car model does not exist by ID."));

        carModelDao.delete(existingCarModel);

    }

    @Override
    public Car addCarModelToCarByName(int id, String modelName) {
        Car existingCar=carDao.findCarById(id)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        CarModel existingCarModel=carModelDao.findCarModelByModelName(modelName)
                        .orElseThrow(() -> new RuntimeException("This car model does not exist by name."));


        existingCar.setCarBrand(existingCarModel.getCarBrand());
        existingCar.setCarModel(existingCarModel);
        return existingCar;



    }

    @Override
    public Car addCarModelToCarById(int carId, int modelId) {
        Car existingCar=carDao.findCarById(carId)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        CarModel existingCarModel=carModelDao.findCarModelById(modelId)
                .orElseThrow(() -> new RuntimeException("This car model does not exist by name."));


        existingCar.setCarBrand(existingCarModel.getCarBrand());
        existingCar.setCarModel(existingCarModel);
        carDao.save(existingCar);
        return existingCar;

    }

    @Override
    public Car updateCarModelInCarByName(int id, String modelName) {
        Car existingCar=carDao.findCarById(id)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        CarModel existingCarModel=carModelDao.findCarModelByModelName(modelName)
                .orElseThrow(() -> new RuntimeException("This car model does not exist by name."));

        existingCar.setCarBrand(null);
        existingCar.setCarBrand(existingCarModel.getCarBrand());
        existingCar.setCarModel(null);
        existingCar.setCarModel(existingCarModel);
        return existingCar;







    }

    @Override
    public Car updateCarModelInCarById(int id, int modelId) {

        Car existingCar=carDao.findCarById(id)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        CarModel existingCarModel=carModelDao.findCarModelById(modelId)
                .orElseThrow(() -> new RuntimeException("This car model does not exist by name."));

        existingCar.setCarBrand(null);
        existingCar.setCarBrand(existingCarModel.getCarBrand());
        existingCar.setCarModel(null);
        existingCar.setCarModel(existingCarModel);
        return existingCar;
    }

    @Override
    public Car deleteCarModelInCar(int id) {
        Car existingCar=carDao.findCarById(id)
                .orElseThrow(() -> new RuntimeException("This car does not exist by id."));

        existingCar.setCarModel(null);
        return existingCar;
    }

    @Override
    public List<String> getAllCarModels() {
        return carModelDao.findAll()
                .stream().map(CarModel::getModelName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllCarModelsInBrand(int id) {
        return carModelDao.findCarModelsByCarBrandId(id)
                .stream().map(CarModel::getModelName).collect(Collectors.toList());
    }
}
