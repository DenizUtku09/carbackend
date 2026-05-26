package ashina.carrental.car.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonSerialize
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "is_available")
    private boolean isAvailable;

    @OneToOne
    @JoinColumn(name = "transmission_type_id")
    TransmissionType transmissionType;
    @OneToOne
    @JoinColumn(name = "price_id")
    Price price;
    @OneToOne
    @JoinColumn(name = "car_type_id")
    CarType carType;
    @OneToOne
    @JoinColumn(name = "car_brand_id")
    CarBrand carBrand;
    @OneToOne()
    @JoinColumn(name = "fuel_type_id")
    FuelType fuelType;
    @OneToOne
    @JoinColumn(name = "insurance_id")
    Insurance insurance;
    @OneToOne
    @JoinColumn(name = "color_id")
    Color color;
    @OneToOne
    @JoinColumn(name = "model_id")
    CarModel carModel;






}
