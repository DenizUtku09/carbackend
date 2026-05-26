package ashina.carrental.car.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "discount_rate")
    private int discountRate;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name ="end_date")
    private LocalDateTime endDate;

    @OneToOne
    private Price price;


}
