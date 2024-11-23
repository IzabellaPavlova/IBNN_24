package org.ibmm.ibmm24.service;

import org.ibmm.ibmm24.dto.CarRentalOfferDto;

import java.util.List;

public interface EngineLogicService {
    void cleanup();

    void createOffers(List<CarRentalOfferDto> orders);
}
