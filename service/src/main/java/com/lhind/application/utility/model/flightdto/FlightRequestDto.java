package com.lhind.application.utility.model.flightdto;

import com.lhind.application.utility.validation.datevalidation.FutureDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Flight Request")
public class FlightRequestDto {

    @NotBlank(message = "Departing city is mandatory")
    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Departing city of the flight", example = "rome", required = true)
    private String from;

    @NotBlank(message = "Arrival city is mandatory")
    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Arrival city of the flight", example = "berlin", required = true)
    private String to;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(notes = "Departure date of the flight", example = "2021-12-25", required = true)
    private Date departureDate;

    @DateTimeFormat(pattern = "hh:mm:ss")
    @ApiModelProperty(notes = "Departure time of the flight", dataType = "Time", example = "12:00:00", required = true)
    private Time departureTime;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(notes = "Arrival date of the flight", example = "2021-12-25", required = true)
    private Date arrivalDate;

    @DateTimeFormat(pattern = "hh:mm:ss")
    @ApiModelProperty(notes = "Arrival time of the flight", dataType = "Time", example = "14:00:00", required = true)
    private Time arrivalTime;

    @NotBlank(message = "Arrival city is mandatory")
    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Airline providing the flight", example = "Lufthansa", required = true)
    private String airline;

}
