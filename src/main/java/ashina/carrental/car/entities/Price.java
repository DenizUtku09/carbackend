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
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "price")
    private int price;
    @Column(name = "available_discount")
    private boolean availableDiscount;

    @OneToMany
    private List<Discount> discounts=new ArrayList<>();
    @OneToMany
    @JoinTable(name = "car")
    private List<Car> cars=new ArrayList<>();


}
