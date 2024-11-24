package org.ibmm.ibmm24.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

import org.ibmm.ibmm24.dto.CarRentalOfferDto;
import org.ibmm.ibmm24.dto.request.CreateOffersInput;
import org.ibmm.ibmm24.dto.request.OfferQueryInput;
import org.ibmm.ibmm24.dto.response.CreateOffersOutput;
import org.ibmm.ibmm24.dto.response.OfferQueryOutput;
import org.ibmm.ibmm24.service.CreateOffersService;
import org.ibmm.ibmm24.service.EngineLogicService;
import org.ibmm.ibmm24.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class EngineApiController {

    @Autowired
    CreateOffersService createOffersService;

    @Autowired
    EngineLogicService engineLogicService;

    @GetMapping("/api/offers")
    ResponseEntity<OfferQueryOutput> readOffers(
        @RequestParam("regionID") Integer regionID,
        @RequestParam("timeRangeStart") Long timeRangeStart,
        @RequestParam("timeRangeEnd") Long timeRangeEnd,
        @RequestParam("numberDays") Integer numberDays,
        @RequestParam("sortOrder") String sortOrder,
        @RequestParam("page") Long page,
        @RequestParam("pageSize") Long pageSize,
        @RequestParam("priceRangeWidth") Long priceRangeWidth,
        @RequestParam("minFreeKilometerWidth") Long minFreeKilometerWidth,
        @RequestParam(value = "minNumberSeats", required = false) Integer minNumberSeats,
        @RequestParam(value = "minPrice", required = false) Integer minPrice,
        @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
        @RequestParam(value = "carType", required = false) String carType,
        @RequestParam(value = "onlyVollkasko", required = false) Boolean onlyVollkasko,
        @RequestParam(value = "minFreeKilometers", required = false) Integer minFreeKilometers
    ) {
        OfferQueryInput input = new OfferQueryInput();
        input.setRegionID(regionID);
        input.setTimeRangeStart(timeRangeStart);
        input.setTimeRangeEnd(timeRangeEnd);
        input.setNumberDays(numberDays);
        input.setSortOrder(sortOrder);
        input.setPage(page);
        input.setPageSize(pageSize);
        input.setPriceRangeWidth(priceRangeWidth);
        input.setMinFreeKilometerWidth(minFreeKilometerWidth);
        input.setMinNumberSeats(minNumberSeats);
        input.setMinPrice(minPrice);
        input.setMaxPrice(maxPrice);
        input.setCarType(carType);
        input.setOnlyVollkasko(onlyVollkasko);
        log.info(input.toString());
        OfferQueryOutput output = new OfferQueryOutput();
        List<CarRentalOfferDto> offers = engineLogicService.mandatoryFilters(
                regionID, timeRangeStart, timeRangeEnd, numberDays, sortOrder
        );
        output.setOffers(offers);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

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
