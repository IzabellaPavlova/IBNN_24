package org.ibmm.ibmm24.dto;

public record CarRentalOfferDto(
        String ID,
        String data,
        int mostSpecificRegionID,
        long startDate,
        long endDate,
        int numberSeats,
        int price,
        String carType,
        boolean hasVollkasko,
        int freeKilometers
) {}
