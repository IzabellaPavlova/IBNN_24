package org.ibmm.ibmm24.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "car_rental_offers")  // Specify the table name in the database
public class CarRentalOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "offer_id", nullable = false)
    private String offerID;

    @Column(name = "region_id", nullable = false)
    private int regionID;

    @Column(name = "car_type", nullable = false)
    private String carType;

    @Column(name = "number_days", nullable = false)
    private int numberDays;

    @Column(name = "number_seats", nullable = false)
    private int numberSeats;

    @Column(name = "start_timestamp", nullable = false)
    private ZonedDateTime startTimestamp;

    @Column(name = "end_timestamp", nullable = false)
    private ZonedDateTime endTimestamp;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "has_vollkasko", nullable = false)
    private boolean hasVollkasko;

    @Column(name = "free_kilometers", nullable = false)
    private int freeKilometers;

    // Getters and Setters (or use Lombok for automatic generation)
    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getNumberDays() {
        return numberDays;
    }

    public void setNumberDays(int numberDays) {
        this.numberDays = numberDays;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public ZonedDateTime getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(ZonedDateTime startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public ZonedDateTime getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(ZonedDateTime endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
}
