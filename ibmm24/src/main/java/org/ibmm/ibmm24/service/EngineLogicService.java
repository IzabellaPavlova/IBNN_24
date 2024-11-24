package org.ibmm.ibmm24.service;

import org.ibmm.ibmm24.dto.CarRentalOfferDto;
import org.ibmm.ibmm24.dto.request.OfferQueryInput;
import org.ibmm.ibmm24.dto.response.OfferQueryOutput;

import java.util.List;

public interface EngineLogicService {
    OfferQueryOutput filterAndAggregateOrders(OfferQueryInput input);

    List<CarRentalOfferDto> mandatoryFilters();

    List<CarRentalOfferDto> mandatoryFilters(
            Integer regionID,
            Long timeRangeStart,
            Long timeRangeEnd,
            Integer numberDays,
            String sortOrder
    );

    void cleanup();

    void createOffers(List<CarRentalOfferDto> orders);
}
