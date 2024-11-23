package org.ibmm.ibmm24.dto;

import java.time.ZonedDateTime;

public record CarRentalOfferDto(
        String ID,
        String data,
        String mostSpecificRegionID,
        String carType,
        int startDate,
        int endDate,
        int numberSeats,
        int price,
        boolean hasVollkasko,
        int freeKilometers
) {}
