package com.lhind.application.utility.model.flightdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@Getter
@Setter
public class FlightResponseDto {

    private Long id;
    private String from;
    private String to;
    private Date departureDate;
    private Time departureTime;
    private Date arrivalDate;
    private Time arrivalTime;
    private String airline;

}
