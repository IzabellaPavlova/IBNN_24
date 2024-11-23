package org.ibmm.ibmm24.repository;

import org.ibmm.ibmm24.dto.CarRentalOfferDto;

import java.util.List;

public interface CarRentalOfferRepository {
    void saveOffers(List<CarRentalOfferDto> offers);

    void deleteOffers();
}
