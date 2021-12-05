package com.lhind.application.utility.model.FlightDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Date departureDate;

    private Date arrivalDate;

}
