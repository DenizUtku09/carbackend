package ashina.carrental.address.entities.concretes;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name="country")
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor

public class Country {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@Column(name="id")
	@JsonProperty("id")
	private int countryId;
	@Column(name="name")
	@JsonProperty("countryName")
	private String countryName;
	@JsonManagedReference
    @OneToMany(orphanRemoval = true)
	@JoinColumn(name="country_id")
    private List<City> cities=new ArrayList<>();
	@JsonManagedReference
	@OneToMany()
	@JoinColumn(name="country_id")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private List<Address> addresses;


}
