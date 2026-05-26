package ashina.carrental.renter.dto;

import java.util.List;

public record CreateListingRequest(
        String title,
        String brand,
        String model,
        Integer year,
        Integer km,
        Integer pricePerDay,
        String color,
        String city,
        String transmission,
        String fuel,
        Integer seats,
        String description,
        List<String> photoUrls
) {}
