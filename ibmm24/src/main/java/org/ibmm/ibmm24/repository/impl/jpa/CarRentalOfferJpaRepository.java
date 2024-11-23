package org.ibmm.ibmm24.repository.impl.jpa;

import jakarta.transaction.Transactional;
import org.ibmm.ibmm24.entity.CarRentalOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CarRentalOfferJpaRepository extends JpaRepository<CarRentalOffer, UUID> {
    /**
     * Deletes all offers from the database.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM CarRentalOffer")
    void deleteAllOffers();

    // Custom query to filter offers based on multiple parameters
    @Query("SELECT o FROM CarRentalOffer o WHERE o.mostSpecificRegion.id = :regionId " +
            "AND o.startDate >= :timeRangeStart " +
            "AND o.endDate <= :timeRangeEnd " +
            "AND o.fullDays >= :numberDays " +
            "AND o.price >= :minPrice AND o.price < :maxPrice " +
            "AND o.numberSeats >= :minNumberSeats " +
            "AND (o.carType = :carType OR :carType IS NULL) " +
            "AND (o.hasVollkasko = :onlyVollkasko OR :onlyVollkasko IS NULL) " +
            "ORDER BY o.price ASC")
    List<CarRentalOffer> findOffersByFilters(
            @Param("regionId") int regionId,
            @Param("timeRangeStart") long timeRangeStart,
            @Param("timeRangeEnd") long timeRangeEnd,
            @Param("numberDays") int numberDays,
            @Param("minPrice") int minPrice,
            @Param("maxPrice") int maxPrice,
            @Param("minNumberSeats") int minNumberSeats,
            @Param("carType") String carType,
            @Param("onlyVollkasko") Boolean onlyVollkasko);
}
