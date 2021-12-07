package com.lhind.application.utility.model.FlightDto;

import com.lhind.application.utility.validation.FlightAvailableDates;
import com.lhind.application.utility.validation.FutureDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@FlightAvailableDates
public class FlightPatchDto {

    @Size(min = 3, max = 20, message = "Departing city should be between 3 and 20 characters")
    private String from;

    @Size(min = 3, max = 20, message = "Arrival city should be between 3 and 20 characters")
    private String to;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date departureDate;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date arrivalDate;

}
