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
    private Long timeRangeStart;  // Start timestamp (ms since UNIX epoch)

    @NotNull(message = "Time range end is required.")
    private Long timeRangeEnd;    // End timestamp (ms since UNIX epoch)

    @NotNull(message = "Number of days is required.")
    private Integer numberDays;   // Number of full days the car is available

    @NotNull(message = "Sort order is required.")
    @Pattern(regexp = "price-(asc|desc)", message = "Sort order must be 'price-asc' or 'price-desc'.")
    private String sortOrder;     // Sort order ("price-asc" or "price-desc")

    @NotNull(message = "Page number is required.")
    private Long page;            // Page number for pagination

    @NotNull(message = "Page size is required.")
    private Long pageSize;        // Number of offers per page

    @NotNull(message = "Price range width is required.")
    private Long priceRangeWidth; // Width of the price range blocks in cents

    @NotNull(message = "Minimum free kilometer width is required.")
    private Long minFreeKilometerWidth;   // Width of the min free kilometer in km

    // Optional fields
    private Integer minNumberSeats;       // Minimum number of seats for returned cars

    private Integer minPrice;             // Minimum price in cents

    private Integer maxPrice;             // Maximum price in cents

    private String carType;               // Car type (small, sports, luxury, family)

    private Boolean onlyVollkasko;        // Whether only offers with Vollkasko are returned

    private Integer minFreeKilometer;     // Minimum number of kilometers included for free
}
