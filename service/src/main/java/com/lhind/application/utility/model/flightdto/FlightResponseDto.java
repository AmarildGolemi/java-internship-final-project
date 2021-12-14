package com.lhind.application.utility.model.flightdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "Flight Response")
public class FlightResponseDto {

    @ApiModelProperty(notes = "Unique ID of the flight", example = "123")
    private Long id;

    @ApiModelProperty(notes = "Departing city of the flight", example = "rome")
    private String from;

    @ApiModelProperty(notes = "Arrival city of the flight", example = "berlin")
    private String to;

    @ApiModelProperty(notes = "Departure date of the flight", example = "2021-12-25")
    private Date departureDate;

    @ApiModelProperty(notes = "Departure time of the flight", example = "12:00:00")
    private Time departureTime;

    @ApiModelProperty(notes = "Arrival date of the flight", example = "2021-12-25")
    private Date arrivalDate;

    @ApiModelProperty(notes = "Arrival time of the flight", example = "14:00:00")
    private Time arrivalTime;

    @ApiModelProperty(notes = "Airline providing the flight", example = "Lufthansa")
    private String airline;

}
