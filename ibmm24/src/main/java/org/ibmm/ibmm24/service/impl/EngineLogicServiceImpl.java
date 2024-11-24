package org.ibmm.ibmm24.service.impl;

import lombok.AllArgsConstructor;
import org.ibmm.ibmm24.dto.CarRentalOfferDto;
import org.ibmm.ibmm24.dto.RegionNode;
import org.ibmm.ibmm24.dto.request.OfferQueryInput;
import org.ibmm.ibmm24.dto.response.OfferQueryOutput;
import org.ibmm.ibmm24.repository.impl.CarRentalOfferRepositoryImpl;
import org.ibmm.ibmm24.service.EngineLogicService;
import org.ibmm.ibmm24.utility.RegionFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EngineLogicServiceImpl implements EngineLogicService {
    @Autowired
    CarRentalOfferRepositoryImpl carRentalOfferRepository;

    @Autowired
    private final RegionNode root;

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
    public List<CarRentalOfferDto> mandatoryFilters() {
        return List.of();
    }

    @Override
    public List<CarRentalOfferDto> mandatoryFilters(
            Integer regionID,
            Long timeRangeStart,
            Long timeRangeEnd,
            Integer numberDays,
            String sortOrder
    ) {
        List<Integer> leaves = RegionFinder.findLeavesById(root, regionID);
        return carRentalOfferRepository.mandatoryFilters(leaves, timeRangeStart, timeRangeEnd, numberDays, sortOrder);
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
