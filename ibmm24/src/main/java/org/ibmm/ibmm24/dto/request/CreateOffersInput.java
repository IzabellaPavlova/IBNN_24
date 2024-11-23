package org.ibmm.ibmm24.dto.request;

import org.ibmm.ibmm24.dto.CarRentalOfferDto;

import java.io.Serializable;
import java.util.List;

public class CreateOffersInput implements Serializable {
    private List<CarRentalOfferDto> offers;
}
