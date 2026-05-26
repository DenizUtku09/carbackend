package ashina.carrental.address.entities.concretes.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {
    private String countryName;
    private int countryId;
    private List<String> cities;

}
