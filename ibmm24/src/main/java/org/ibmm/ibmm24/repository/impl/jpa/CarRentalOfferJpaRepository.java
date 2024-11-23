package org.ibmm.ibmm24.repository.impl.jpa;

import jakarta.transaction.Transactional;
import org.ibmm.ibmm24.entity.CarRentalOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CarRentalOfferJpaRepository extends JpaRepository<CarRentalOffer, UUID> {
    /**
     * Deletes all offers from the database.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM CarRentalOffer")
    void deleteAllOffers();
}
