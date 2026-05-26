package ashina.carrental.address.entities.concretes.dtos;

import ashina.carrental.address.entities.concretes.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CityDTO {
    private String cityName;
    private int cityId;
    private List<String> streets;


    public <R> CityDTO(String cityName, int cityId, R collect) {
    }
}
