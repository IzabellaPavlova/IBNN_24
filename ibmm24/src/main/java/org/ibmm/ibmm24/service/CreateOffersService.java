package org.ibmm.ibmm24.service;

import org.ibmm.ibmm24.dto.request.CreateOffersInput;
import org.ibmm.ibmm24.dto.response.CreateOffersOutput;

public interface CreateOffersService {
    CreateOffersOutput createOffers(CreateOffersInput input);
}
