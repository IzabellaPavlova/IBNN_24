package org.ibmm.ibmm24.dto;

import java.time.ZonedDateTime;

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
