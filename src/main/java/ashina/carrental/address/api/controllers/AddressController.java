package ashina.carrental.address.api.controllers;
import ashina.carrental.address.business.abstracts.AddressService;
import ashina.carrental.address.entities.concretes.dtos.requests.address.AddAddressRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.address.DeleteAddressByIdRequest;
import org.springframework.web.bind.annotation.*;
import ashina.carrental.address.entities.concretes.Address;
@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;
    public AddressController(AddressService addressService){
        super();
        this.addressService=addressService;
    }
    @PostMapping("/AddAddress")
    public Address addAddress(@RequestBody AddAddressRequest addAddressRequest){
        return addressService.addAddress(addAddressRequest);

    }
    @GetMapping("/GetAddressById/{addressId}")
    public Address getAddressById(@PathVariable int addressId){return addressService.getAddressById(addressId);}

    @DeleteMapping("/DeleteAddress")
    public void deleteAddress(DeleteAddressByIdRequest deleteAddressByIdRequest){addressService.deleteAddress(deleteAddressByIdRequest);}


	
	

}
