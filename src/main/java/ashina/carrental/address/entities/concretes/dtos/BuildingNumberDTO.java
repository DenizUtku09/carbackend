package ashina.carrental.address.entities.concretes.dtos;

import ashina.carrental.address.entities.concretes.Street;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingNumberDTO {
    private int buildingNo;
    private int buildingNumberId;
    private Street street;

}
