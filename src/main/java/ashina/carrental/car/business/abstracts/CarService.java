package ashina.carrental.car.business.abstracts;

import ashina.carrental.car.entities.Car;

import java.util.List;

public interface CarService {
    public List<Car> findCarsWithSorting(String field);


}
