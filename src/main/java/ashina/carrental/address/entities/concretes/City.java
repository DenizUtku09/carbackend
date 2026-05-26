package ashina.carrental.address.entities.concretes;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name="city")
@Builder
@Getter
@Setter
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="id")
    @JsonProperty("id")
	private int cityId;
    @Column(name="name")
    @JsonProperty("city_name")
	private String cityName;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @JsonManagedReference
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "city_id")
    private List<Street> streets=new ArrayList<>();

    @JsonManagedReference
    @OneToMany
    @JoinColumn(name = "city_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<Address> address;



}
