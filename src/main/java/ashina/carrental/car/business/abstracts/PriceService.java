package ashina.carrental.car.business.abstracts;

import ashina.carrental.car.entities.Price;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface PriceService {


    Price addPriceToCar(int id,int price);
    void deletePriceInCar(int id);
    Price updatePriceInCar(int id,int price);


}
