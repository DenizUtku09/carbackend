package ashina.carrental.car.business.concretes;

import ashina.carrental.car.business.abstracts.PriceService;
import ashina.carrental.car.DataAccess.CarDao;
import ashina.carrental.car.DataAccess.PriceDao;

import ashina.carrental.car.entities.Car;
import ashina.carrental.car.entities.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceManager implements PriceService {
    private final PriceDao priceDao;
    private final CarDao carDao;
    @Autowired
    public PriceManager(PriceDao priceDao,CarDao carDao){
        super();
        this.priceDao=priceDao;
        this.carDao=carDao;
    }
    @Override
    public Price addPriceToCar(int id,int price) {
        Car existingCar=carDao.findCarById(id).orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        if(existingCar.getPrice()!=null){throw new RuntimeException("This car already has a price.");}
        else{existingCar.getPrice().setPrice(price);
        return existingCar.getPrice();}
    }

    @Override
    public void deletePriceInCar(int id) {
        Car existingCar=carDao.findCarById(id).orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        if(existingCar.getPrice()==null){throw new RuntimeException("Price already does not exists.");}
        else{ existingCar.setPrice(null);}
    }

    @Override
    public Price updatePriceInCar(int id, int price) {
        Car existingCar=carDao.findCarById(id).orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        if(existingCar.getPrice()==null){throw new RuntimeException("This car does not have a price.");}
        else{
            if(existingCar.getPrice().getPrice() == price){throw new RuntimeException("This price update is same as before.");}
            existingCar.getPrice().setPrice(price);
            return existingCar.getPrice();
        }
    }

}
