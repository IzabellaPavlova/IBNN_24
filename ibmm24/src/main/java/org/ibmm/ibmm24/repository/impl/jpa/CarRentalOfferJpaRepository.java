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
    @Query("SELECT o FROM CarRentalOffer o WHERE o.mostSpecificRegion IN :leaves " +
            "AND o.startDate >= :timeRangeStart " +
            "AND o.endDate <= :timeRangeEnd " +
            "AND o.fullDays >= :numberDays ")
    List<CarRentalOffer> findOffersByFilters(
            @Param("leaves") List<Integer> leaves,
            @Param("timeRangeStart") long timeRangeStart,
            @Param("timeRangeEnd") long timeRangeEnd,
            @Param("numberDays") int numberDays,
            @Param("order") String order
            );
}
