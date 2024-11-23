package org.ibmm.ibmm24.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.ibmm.ibmm24.dto.CarRentalOfferDto;
import org.ibmm.ibmm24.entity.CarRentalOffer;
import org.ibmm.ibmm24.repository.CarRentalOfferRepository;
import org.ibmm.ibmm24.repository.impl.jpa.CarRentalOfferJpaRepository;
import org.ibmm.ibmm24.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Slf4j
public class CarRentalOfferRepositoryImpl implements CarRentalOfferRepository {
    @Autowired
    CarRentalOfferJpaRepository carRentalOfferJpaRepository;


    @Override
    public void saveOffers(List<CarRentalOfferDto> offers) {
        List<CarRentalOffer> offersEnitites = offers.stream().map(this::fromDto).toList();
        carRentalOfferJpaRepository.saveAll(offersEnitites);
    }

    @Override
    public void deleteOffers() {
        carRentalOfferJpaRepository.deleteAllOffers();
    }

    private CarRentalOffer fromDto(CarRentalOfferDto dto) {
        CarRentalOffer entity = new CarRentalOffer();
        entity.setId(UUID.fromString(dto.ID()));
        entity.setMostSpecificRegion(dto.mostSpecificRegionID());
        entity.setData(dto.data());
        entity.setStartDate(dto.startDate());
        entity.setEndDate(dto.endDate());
        int fullDays = (int) Utility.calculateFullDaysBetween(dto.startDate(), dto.endDate());
        entity.setFullDays(fullDays);
        entity.setNumberSeats(dto.numberSeats());
        entity.setPrice(dto.price());
        entity.setCarType(dto.carType());
        entity.setHasVollkasko(dto.hasVollkasko());
        entity.setFreeKilometers(dto.freeKilometers());
        log.info(entity.toString());
        return entity;
    }
}
