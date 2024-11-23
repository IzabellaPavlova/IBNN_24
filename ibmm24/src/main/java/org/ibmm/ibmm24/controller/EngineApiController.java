package org.ibmm.ibmm24.controller;

import org.ibmm.ibmm24.dto.request.CreateOffersInput;
import org.ibmm.ibmm24.dto.response.CreateOffersOutput;
import org.ibmm.ibmm24.service.CreateOffersService;
import org.ibmm.ibmm24.service.EngineLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EngineApiController {

    @Autowired
    CreateOffersService createOffersService;

    @Autowired
    EngineLogicService engineLogicService;

    @PostMapping("/api/offers")
    ResponseEntity<CreateOffersOutput> createOffers(@RequestBody CreateOffersInput input) {
        engineLogicService.createOffers(input.getOffers());
        return new ResponseEntity<>(createOffersService.createOffers(input), HttpStatus.OK);
    }
    @DeleteMapping("/api/offers")
    ResponseEntity<String> cleanUpOffers() {
        engineLogicService.cleanup();
        return new ResponseEntity<>(
                "Offers cleaned",
                HttpStatus.OK);
    }
}
