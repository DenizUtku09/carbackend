package ashina.carrental.address.business.concretes;
import ashina.carrental.address.dataAccess.abstracts.*;
import ashina.carrental.address.entities.concretes.*;
import ashina.carrental.address.entities.concretes.dtos.mappers.AddressDTOMapper;
import ashina.carrental.address.entities.concretes.dtos.requests.address.AddAddressRequest;
import ashina.carrental.address.entities.concretes.dtos.requests.address.DeleteAddressByIdRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ashina.carrental.address.business.abstracts.AddressService;

@Service
public class AddressManager implements AddressService {
	private final AddressDao addressDao;
	private final StreetDao streetDao;
	private final CountryDao countryDao;
	private final BuildingNumberDao buildingNumberDao;
	private final CityDao cityDao;
	private final AddressDTOMapper addressDTOMapper;
	@Autowired
	public AddressManager (AddressDao addressDao, StreetDao streetDao, CountryDao countryDao, CityDao cityDao,BuildingNumberDao buildingNumberDao,AddressDTOMapper addressDTOMapper) {
		super();
		this.addressDao=addressDao;
		this.buildingNumberDao=buildingNumberDao;
		this.countryDao=countryDao;
		this.streetDao=streetDao;
		this.cityDao=cityDao;
		this.addressDTOMapper=addressDTOMapper;
	}
	@Override
	public Address addAddress(AddAddressRequest addAddressRequest) {
		Address newAddress=new Address();
		Country existingCountry=countryDao.findByCountryName(addAddressRequest.countryName());
		if(existingCountry!=null){newAddress.setCountry(existingCountry);}
		City existingCity=cityDao.findCityByCityName(addAddressRequest.cityName());
		if(existingCity!=null){newAddress.setCity(existingCity);}
		Street existingStreet=streetDao.findByStreetName(addAddressRequest.streetName());
		if(existingStreet!=null){newAddress.setStreet(existingStreet);}
		BuildingNumber existingBuildingNumber=buildingNumberDao.findByBuildingNo(addAddressRequest.buildingNo());
		if(existingBuildingNumber!=null){newAddress.setBuildingNumber(existingBuildingNumber);}
		return addressDao.save(newAddress);
	}
	@Override
	public Address getAddressById(int addressId) {
		Address existingAddress=addressDao.findAddressByAddressId(addressId);
		if(existingAddress==null){throw new EntityNotFoundException("This address does not exist.");}
		else{return addressDao.findAddressByAddressId(addressId);}}

	@Override
	public void deleteAddress(DeleteAddressByIdRequest deleteAddressByIdRequest) {
		Address existingAddress=addressDao.findAddressByAddressId(deleteAddressByIdRequest.addressId());
		if(existingAddress!=null){
			addressDao.delete(existingAddress);}}}
