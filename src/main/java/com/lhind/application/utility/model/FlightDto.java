package com.lhind.application.utility.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class FlightDto {

    @NotBlank(message = "Departing city is mandatory")
    @Size(min = 3, max = 20)
    private String from;

    @NotBlank(message = "Arrival city is mandatory")
    @Size(min = 3, max = 20)
    private String to;

    @NotBlank(message = "Departure date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future
    private Date departureDate;

    @NotBlank(message = "Arrival date is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future
    private Date arrivalDate;

}
