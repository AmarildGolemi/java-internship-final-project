package com.lhind.application.utility.model.flightdto;

import com.lhind.application.utility.validation.datevalidation.PatchFutureDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@Getter
@Setter
public class FlightPatchDto {

    @Size(min = 3, max = 20)
    private String from;

    @Size(min = 3, max = 20)
    private String to;

    @PatchFutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date departureDate;

    private Time departureTime;

    @PatchFutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date arrivalDate;

    private Time arrivalTime;

}
