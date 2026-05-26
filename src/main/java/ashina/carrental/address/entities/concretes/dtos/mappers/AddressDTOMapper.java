package ashina.carrental.address.entities.concretes.dtos.mappers;

import ashina.carrental.address.entities.concretes.Address;
import ashina.carrental.address.entities.concretes.dtos.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class AddressDTOMapper implements Function<Address, AddressDTO> {

    @Override
    public AddressDTO apply(Address address) {
        return new AddressDTO(
                address.getCity().getCityName(),
                address.getCountry().getCountryName(),
                address.getStreet().getStreetName(),
                address.getBuildingNumber().getBuildingNo()
        );
    }
}
