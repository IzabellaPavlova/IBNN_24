package org.ibmm.ibmm24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offers")
public class CarRentalOffer {

    @Id
    private UUID id; // Corresponds to the UUID primary key

    @Column(name = "most_specific_region", nullable = false)
    private int mostSpecificRegion;

    @Column(nullable = false, length = 255)
    private String data;

    @Column(name = "start_date", nullable = false)
    private long startDate; // Using long for BIGINT

    @Column(name = "end_date", nullable = false)
    private long endDate; // Using long for BIGINT

    @Column(name = "full_days", nullable = false)
    private int fullDays; // SERIAL in DB is managed by sequences, we can map to an int

    @Column(name = "number_seats", nullable = false)
    private int numberSeats;

    @Column(nullable = false)
    private int price;

    @Column(name = "car_type", nullable = false, length = 255)
    private String carType;

    @Column(name = "has_vollkasko", nullable = false)
    private boolean hasVollkasko;

    @Column(name = "free_kilometers", nullable = false)
    private int freeKilometers;
}
