package org.ibmm.ibmm24.repository;

import org.ibmm.ibmm24.dto.CarRentalOfferDto;

import java.util.List;

public interface CarRentalOfferRepository {

    List<CarRentalOfferDto> filterOffers(
            int regionId,
            long timeRangeStart,
            long timeRangeEnd,
            int numberDays,
            int minPrice,
            int maxPrice,
            int minNumberSeats,
            String carType,
            Boolean onlyVollkasko
    );

    void saveOffers(List<CarRentalOfferDto> offers);

    void deleteOffers();
}
