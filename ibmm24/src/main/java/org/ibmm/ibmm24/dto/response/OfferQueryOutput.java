package org.ibmm.ibmm24.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ibmm.ibmm24.dto.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferQueryOutput {
    private List<CarRentalOfferDto> offers;
//    private List<PriceRange> priceRanges;                   // Price range buckets
//    private CarTypeCount carTypeCounts;                     // Car type count aggregation
//    private List<SeatsCount> seatsCount;                    // Seat count buckets
//    private List<FreeKilometerRange> freeKilometerRange;    // Free kilometer range buckets
//    private VollkaskoCount vollkaskoCount;
}
