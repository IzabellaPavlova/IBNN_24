package org.ibmm.ibmm24.dto;

import java.time.ZonedDateTime;

public record CarRentalOfferDto(
        String offerID,
        int regionID,
        String carType,
        int numberDays,
        int numberSeats,
        ZonedDateTime startTimestamp,
        ZonedDateTime endTimestamp,
        int price,
        boolean hasVollkasko,
        int freeKilometers
) {}
