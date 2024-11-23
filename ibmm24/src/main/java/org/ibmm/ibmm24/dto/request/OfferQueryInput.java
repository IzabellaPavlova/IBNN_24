package org.ibmm.ibmm24.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OfferQueryInput {

    // Required fields
    @NotNull(message = "Region ID is required.")
    private Integer regionID;  // Region ID

    @NotNull(message = "Time range start is required.")
    @Min(value = 0, message = "Start timestamp must be a non-negative value.")
    private Long timeRangeStart;  // Start timestamp (ms since UNIX epoch)

    @NotNull(message = "Time range end is required.")
    @Min(value = 0, message = "End timestamp must be a non-negative value.")
    private Long timeRangeEnd;    // End timestamp (ms since UNIX epoch)

    @NotNull(message = "Number of days is required.")
    @Min(value = 1, message = "Number of days must be at least 1.")
    private Integer numberDays;   // Number of full days the car is available

    @NotNull(message = "Sort order is required.")
    @Pattern(regexp = "price-(asc|desc)", message = "Sort order must be 'price-asc' or 'price-desc'.")
    private String sortOrder;     // Sort order ("price-asc" or "price-desc")

    @NotNull(message = "Page number is required.")
    @Min(value = 1, message = "Page number must be at least 1.")
    private Long page;            // Page number for pagination

    @NotNull(message = "Page size is required.")
    @Min(value = 1, message = "Page size must be at least 1.")
    private Long pageSize;        // Number of offers per page

    @NotNull(message = "Price range width is required.")
    @Min(value = 1, message = "Price range width must be at least 1.")
    private Long priceRangeWidth; // Width of the price range blocks in cents

    @NotNull(message = "Minimum free kilometer width is required.")
    @Min(value = 1, message = "Minimum free kilometer width must be at least 1.")
    private Long minFreeKilometerWidth;   // Width of the min free kilometer in km

    // Optional fields
    @Min(value = 1, message = "Minimum number of seats must be at least 1.")
    private Integer minNumberSeats;       // Minimum number of seats for returned cars

    @Min(value = 0, message = "Minimum price must be a non-negative value.")
    private Integer minPrice;             // Minimum price in cents

    @Min(value = 0, message = "Maximum price must be a non-negative value.")
    private Integer maxPrice;             // Maximum price in cents

    @Pattern(regexp = "small|sports|luxury|family", message = "Car type must be one of the following: small, sports, luxury, family.")
    private String carType;               // Car type (small, sports, luxury, family)

    private Boolean onlyVollkasko;        // Whether only offers with Vollkasko are returned

    @Min(value = 0, message = "Minimum free kilometers must be a non-negative value.")
    private Integer minFreeKilometer;     // Minimum number of kilometers included for free
}
