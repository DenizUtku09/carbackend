package ashina.carrental.address.entities.concretes;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name="street")
@Builder
@Getter
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Street {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	@JsonProperty("id")
	private int streetId;
	
	@Column(name="name")
	@JsonProperty("streetName")
	private String streetName;


	@JsonBackReference
	@ManyToOne()
	@JoinColumn(name = "city_id")
	private City city;
	@JsonManagedReference
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name="street_id")
    private List<BuildingNumber> buildingNumbers;
	@JsonManagedReference
	@OneToMany()
	@JoinColumn(name="street_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private List<Address> addresses;
}
