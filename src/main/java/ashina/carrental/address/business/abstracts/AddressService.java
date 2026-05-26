package ashina.carrental.address.business.abstracts;

import ashina.carrental.address.entities.concretes.dtos.requests.address.AddAddressRequest;

import ashina.carrental.address.entities.concretes.Address;
import ashina.carrental.address.entities.concretes.dtos.requests.address.DeleteAddressByIdRequest;

public interface AddressService {
	Address addAddress(AddAddressRequest addAddressRequest);
	Address getAddressById(int addressId);
	void deleteAddress(DeleteAddressByIdRequest deleteAddressByIdRequest);



	

		
	
	

}
