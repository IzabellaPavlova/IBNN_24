package org.ibmm.ibmm24.service.impl;

import org.ibmm.ibmm24.dto.CarRentalOfferDto;
import org.ibmm.ibmm24.dto.request.OfferQueryInput;
import org.ibmm.ibmm24.dto.response.OfferQueryOutput;
import org.ibmm.ibmm24.repository.impl.CarRentalOfferRepositoryImpl;
import org.ibmm.ibmm24.service.EngineLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineLogicServiceImpl implements EngineLogicService {
    @Autowired
    CarRentalOfferRepositoryImpl carRentalOfferRepository;

    @Override
    public OfferQueryOutput filterAndAggregateOrders(OfferQueryInput input) {
        List<CarRentalOfferDto> orders = carRentalOfferRepository.filterOffers(
                input.getRegionID(), input.getTimeRangeStart(), input.getTimeRangeEnd(),
                input.getNumberDays(), input.getMinPrice(), input.getMaxPrice(),
                input.getMinNumberSeats(), input.getCarType(), input.getOnlyVollkasko()
        );
        return new OfferQueryOutput(orders);
    }

    @Override
    public void cleanup() {
        carRentalOfferRepository.deleteOffers();
    }

    @Override
    public void createOffers(List<CarRentalOfferDto> orders) {
        carRentalOfferRepository.saveOffers(orders);
    }
}
