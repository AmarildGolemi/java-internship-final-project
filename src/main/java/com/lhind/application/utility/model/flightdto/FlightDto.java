package com.lhind.application.utility.model.flightdto;

import com.lhind.application.utility.validation.flightvalidation.FlightAvailableDates;
import com.lhind.application.utility.validation.datevalidation.FutureDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@FlightAvailableDates
public class FlightDto {

    @NotBlank(message = "Departing city is mandatory")
    @Size(min = 3, max = 20, message = "Departing city should be between 3 and 20 characters")
    private String from;

    @NotBlank(message = "Arrival city is mandatory")
    @Size(min = 3, max = 20, message = "Arrival city should be between 3 and 20 characters")
    private String to;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date departureDate;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date arrivalDate;

}
