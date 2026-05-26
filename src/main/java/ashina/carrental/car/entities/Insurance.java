package ashina.carrental.car.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "insurance")
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "insurance_year")
    private int insuranceYear;
    @Column(name = "insurance_company")
    private String insuranceCompany;

    @OneToMany
    private List<Car> cars=new ArrayList<>();

}
