package ashina.carrental.car.business.concretes;

import ashina.carrental.car.DataAccess.CarDao;
import ashina.carrental.car.DataAccess.ColorDao;
import ashina.carrental.car.business.abstracts.ColorService;
import ashina.carrental.car.entities.Car;
import ashina.carrental.car.entities.Color;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class ColorManager implements ColorService {
    private final ColorDao colorDao;
    private final CarDao carDao;

    @Override
    public Color addColor(String colorName) {
        Optional<Color> existingColor=colorDao.findColorByColorName(colorName);
        if(existingColor.isPresent()){throw new RuntimeException("This color already exists by name.");}
        Color addedColor=new Color();
        addedColor.setColorName(colorName);
        colorDao.save(addedColor);
        return addedColor;
    }

    @Override
    public Color updateColor(int id, String colorName,String updatedColorName) {
        Color existingColor=colorDao.findColorByIdOrColorName(id,colorName)
                .orElseThrow(() -> new RuntimeException("This color does not exist by ID or name."));

        existingColor.setColorName(updatedColorName);
        return existingColor;


    }

    @Override
    public void deleteColor(int id,String colorName) {
        Color existingColor=colorDao.findColorByIdOrColorName(id, colorName)
                .orElseThrow(() -> new RuntimeException("This color does not exist by name or ID."));
        colorDao.delete(existingColor);


    }

    @Override
    public Car addColorToCar(int carId, int colorId, String colorName) {
        Car existingCar = carDao.findCarById(carId)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        Color existingColor= colorDao.findColorByIdOrColorName(colorId,colorName)
                .orElseThrow(() -> new RuntimeException("This color does not exist by name or ID."));
        existingCar.setColor(existingColor);
        carDao.save(existingCar);
        return existingCar;

    }

    @Override
    public Car updateColorInCar(int carId, int colorId, String colorName) {
        Car existingCar = carDao.findCarById(carId)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        Color existingColor= colorDao.findColorByIdOrColorName(colorId,colorName)
                .orElseThrow(() -> new RuntimeException("This color does not exist by name or ID."));

        existingCar.setColor(null);
        existingCar.setColor(existingColor);
        carDao.save(existingCar);
        return existingCar;
    }

    @Override
    public void deleteColorInCar(int carId) {
        Car existingCar = carDao.findCarById(carId)
                .orElseThrow(() -> new RuntimeException("This car does not exist by ID."));
        existingCar.setColor(null);
    }

    @Override
    public List<Color> getAllColors() {
        return colorDao.findAll();
    }
}
