package com.lhind.application.utility.model.flightdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class FlightDto {

    private Long id;
    private String from;
    private String to;
    private Date departureDate;
    private Date arrivalDate;
    private String airline;

}
