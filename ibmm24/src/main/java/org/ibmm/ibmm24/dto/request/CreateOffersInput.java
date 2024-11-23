package org.ibmm.ibmm24.dto.request;

import org.ibmm.ibmm24.dto.CarRentalOfferDto;

import java.io.Serializable;
import java.util.List;

public class CreateOffersInput implements Serializable {
    private List<CarRentalOfferDto> offers;

    public CreateOffersInput(List<CarRentalOfferDto> offers) {
        this.offers = offers;
    }

    public List<CarRentalOfferDto> getOffers() {
        return offers;
    }

    public void setOffers(List<CarRentalOfferDto> offers) {
        this.offers = offers;
    }
}
