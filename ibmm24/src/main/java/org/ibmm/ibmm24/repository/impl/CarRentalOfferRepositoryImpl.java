package org.ibmm.ibmm24.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.ibmm.ibmm24.dto.CarRentalOfferDto;
import org.ibmm.ibmm24.entity.CarRentalOffer;
import org.ibmm.ibmm24.entity.Region;
import org.ibmm.ibmm24.repository.CarRentalOfferRepository;
import org.ibmm.ibmm24.repository.impl.jpa.CarRentalOfferJpaRepository;
import org.ibmm.ibmm24.repository.impl.jpa.RegionJpaRepository;
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

    @Autowired
    RegionJpaRepository regionJpaRepository;

    @Override
    public List<CarRentalOfferDto> filterOffers(
            int regionId,
            long timeRangeStart,
            long timeRangeEnd,
            int numberDays,
            int minPrice,
            int maxPrice,
            int minNumberSeats,
            String carType,
            Boolean onlyVollkasko
    ) {
        return carRentalOfferJpaRepository.findOffersByFilters(
                    regionId,
                    timeRangeStart,
                    timeRangeEnd,
                    numberDays,
                    minPrice,
                    maxPrice,
                    minNumberSeats,
                    carType,
                    onlyVollkasko
                ).stream()
                .map(this::toDto)
                .toList();
    }

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

        Region region = regionJpaRepository.findById(dto.mostSpecificRegionID())
                .orElseThrow(() -> new RuntimeException("Region not found"));

        entity.setMostSpecificRegion(region);
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

    // Convert CarRentalOffer entity to CarRentalOfferDto
    private CarRentalOfferDto toDto(CarRentalOffer entity) {
        // Extract region ID from the Region entity
        int regionID = entity.getMostSpecificRegion().getId();

        // Map the entity fields to the DTO
        return new CarRentalOfferDto(
                entity.getId().toString(),
                entity.getData(),
                regionID,  // Pass the region ID
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getNumberSeats(),
                entity.getPrice(),
                entity.getCarType(),
                entity.isHasVollkasko(),
                entity.getFreeKilometers()
        );
    }
}
