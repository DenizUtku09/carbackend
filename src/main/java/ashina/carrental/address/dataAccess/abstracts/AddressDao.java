package ashina.carrental.address.dataAccess.abstracts;
import org.springframework.data.jpa.repository.JpaRepository;
import ashina.carrental.address.entities.concretes.Address;
public interface AddressDao extends JpaRepository<Address, Integer> {
    Address findAddressByAddressId(int addressId);

	
	

}
