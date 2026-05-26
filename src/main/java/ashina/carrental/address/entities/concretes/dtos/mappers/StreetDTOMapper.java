package ashina.carrental.address.entities.concretes.dtos.mappers;

import ashina.carrental.address.entities.concretes.BuildingNumber;
import ashina.carrental.address.entities.concretes.Street;
import ashina.carrental.address.entities.concretes.dtos.StreetDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;
@Service
public class StreetDTOMapper implements Function<Street, StreetDTO> {
    @Override
    public StreetDTO apply(Street street) {
        return new StreetDTO(
                street.getStreetName(),
                street.getStreetId(),
                street.getCity(),
                street.getBuildingNumbers()
                        .stream()
                        .map(BuildingNumber::getBuildingNo)
                        .collect(Collectors.toList()
                        ));

    }
}
