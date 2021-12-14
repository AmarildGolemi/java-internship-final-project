package com.lhind.application.utility.model.flightdto;

import com.lhind.application.utility.validation.datevalidation.PatchFutureDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Flight Patch")
public class FlightPatchDto {

    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Departing city of the flight", example = "rome")
    private String from;

    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Arrival city of the flight", example = "berlin")
    private String to;

    @PatchFutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(notes = "Departure date of the flight", example = "2021-12-25")
    private Date departureDate;

    @DateTimeFormat(pattern = "hh:mm:ss")
    @ApiModelProperty(notes = "Departure time of the flight", example = "12:00:00")
    private Time departureTime;

    @PatchFutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(notes = "Arrival date of the flight", example = "2021-12-25")
    private Date arrivalDate;

    @DateTimeFormat(pattern = "hh:mm:ss")
    @ApiModelProperty(notes = "Arrival time of the flight", example = "14:00:00")
    private Time arrivalTime;

}
