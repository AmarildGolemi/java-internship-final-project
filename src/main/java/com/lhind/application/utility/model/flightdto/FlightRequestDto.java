package com.lhind.application.utility.model.flightdto;

import com.lhind.application.utility.validation.datevalidation.FutureDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@Getter
@Setter
public class FlightRequestDto {

    @NotBlank(message = "Departing city is mandatory")
    @Size(min = 3, max = 20)
    private String from;

    @NotBlank(message = "Arrival city is mandatory")
    @Size(min = 3, max = 20)
    private String to;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date departureDate;

    private Time departureTime;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date arrivalDate;

    private Time arrivalTime;

    @NotBlank(message = "Arrival city is mandatory")
    @Size(min = 3, max = 20)
    private String airline;

}
