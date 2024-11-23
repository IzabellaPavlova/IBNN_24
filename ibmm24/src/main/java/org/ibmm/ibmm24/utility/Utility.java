package org.ibmm.ibmm24.utility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Utility {
    public static long calculateFullDaysBetween(long startEpoch, long endEpoch) {
        // Convert epoch to LocalDate
        LocalDate startDate = Instant.ofEpochMilli(startEpoch)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(endEpoch)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Calculate the full days between
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
