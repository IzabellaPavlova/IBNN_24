package org.ibmm.ibmm24.service.impl;

import org.ibmm.ibmm24.dto.request.CreateOffersInput;
import org.ibmm.ibmm24.dto.response.CreateOffersOutput;
import org.ibmm.ibmm24.service.CreateOffersService;
import org.springframework.stereotype.Service;

@Service

public class CreateOffersServiceImpl implements CreateOffersService {
    public CreateOffersServiceImpl() {
    }

    @Override
    public CreateOffersOutput createOffers(CreateOffersInput input) {
        return new CreateOffersOutput("offers created");
    }
}
