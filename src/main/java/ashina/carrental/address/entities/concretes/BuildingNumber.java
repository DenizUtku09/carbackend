package ashina.carrental.address.entities.concretes;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@Entity
@Table(name="building_no")
@NoArgsConstructor
@AllArgsConstructor
public class BuildingNumber {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	@JsonProperty("id")
	private int buildingNumberId;
	@Column(name="no")
	@JsonProperty("building_number")
	private int buildingNo;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "street_id")
    private Street street;

	@JsonManagedReference
	@OneToMany()
	@JoinColumn(name="building_number_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private List<Address> addresses;

}
