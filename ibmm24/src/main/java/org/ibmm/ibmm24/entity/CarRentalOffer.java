package org.ibmm.ibmm24.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
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

    // Getters and setters for all fields
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getMostSpecificRegion() {
        return mostSpecificRegion;
    }

    public void setMostSpecificRegion(int mostSpecificRegion) {
        this.mostSpecificRegion = mostSpecificRegion;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getFullDays() {
        return fullDays;
    }

    public void setFullDays(int fullDays) {
        this.fullDays = fullDays;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public boolean isHasVollkasko() {
        return hasVollkasko;
    }

    public void setHasVollkasko(boolean hasVollkasko) {
        this.hasVollkasko = hasVollkasko;
    }

    public int getFreeKilometers() {
        return freeKilometers;
    }

    public void setFreeKilometers(int freeKilometers) {
        this.freeKilometers = freeKilometers;
    }

    @Override
    public String toString() {
        return "CarRentalOffer{" +
                "id=" + id +
                ", mostSpecificRegion=" + mostSpecificRegion +
                ", data='" + data + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", fullDays=" + fullDays +
                ", numberSeats=" + numberSeats +
                ", price=" + price +
                ", carType='" + carType + '\'' +
                ", hasVollkasko=" + hasVollkasko +
                ", freeKilometers=" + freeKilometers +
                '}';
    }
}
