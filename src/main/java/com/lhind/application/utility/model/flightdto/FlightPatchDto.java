package com.lhind.application.utility.model.flightdto;

import com.lhind.application.utility.validation.datevalidation.FutureDate;
import com.lhind.application.utility.validation.datevalidation.PatchFutureDate;
import com.lhind.application.utility.validation.flightvalidation.FlightPatchAvailableDates;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@FlightPatchAvailableDates
public class FlightPatchDto {

    @Size(min = 3, max = 20)
    private String from;

    @Size(min = 3, max = 20)
    private String to;

    @PatchFutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date departureDate;

    @PatchFutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date arrivalDate;

}
