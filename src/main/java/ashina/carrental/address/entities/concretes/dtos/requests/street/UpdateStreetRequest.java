package ashina.carrental.address.entities.concretes.dtos.requests.street;

import ashina.carrental.address.entities.concretes.City;

public record UpdateStreetRequest(String streetName, City city) {
}
