package org.ibmm.ibmm24.service.impl;

import org.ibmm.ibmm24.dto.CarRentalOfferDto;
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
    public void cleanup() {
        carRentalOfferRepository.deleteOffers();
    }

    @Override
    public void createOffers(List<CarRentalOfferDto> orders) {
        carRentalOfferRepository.saveOffers(orders);
    }
}
