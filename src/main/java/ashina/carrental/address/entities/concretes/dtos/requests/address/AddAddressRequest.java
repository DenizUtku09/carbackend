package ashina.carrental.address.entities.concretes.dtos.requests.address;


public record AddAddressRequest(String countryName,String cityName, String streetName, int buildingNo) {
}
