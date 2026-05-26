package ashina.carrental.address.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="address")
public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int addressId;
    @ManyToOne
    @JoinColumn(name = "building_number_id")
    private BuildingNumber buildingNumber;
    @ManyToOne
    @JoinColumn(name = "street_id")
    private Street street;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
