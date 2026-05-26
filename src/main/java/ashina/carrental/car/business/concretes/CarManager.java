package ashina.carrental.car.business.concretes;

import ashina.carrental.car.business.abstracts.CarService;
import ashina.carrental.car.DataAccess.CarDao;

import ashina.carrental.car.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarManager implements CarService {

    public CarDao carDao;
    @Autowired
    public CarManager(CarDao carDao){
        super();
        this.carDao=carDao;
    }

    @Override
    public List<Car> findCarsWithSorting(String field) {
        return null;
    }
}
