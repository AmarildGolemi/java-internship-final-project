package com.lhind.application.utility.model.tripdto;

import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "Trip Response")
public class TripResponseDto {

    @ApiModelProperty(notes = "Unique ID of the trip", example = "123")
    private Long id;

    @ApiModelProperty(notes = "Reason of the trip", example = "EVENT")
    private TripReason tripReason;

    @ApiModelProperty(notes = "Description of the trip", example = "International Hackathon")
    private String description;

    @ApiModelProperty(notes = "Departing city of the trip", example = "rome")
    private String from;

    @ApiModelProperty(notes = "Arrival city of the trip", example = "berlin")
    private String to;

    @ApiModelProperty(notes = "Departure date of the trip", example = "2021-12-25")
    private Date departureDate;

    @ApiModelProperty(notes = "Arrival date of the trip", example = "2021-12-30")
    private Date arrivalDate;

    @ApiModelProperty(notes = "Status of the trip", example = "CREATED")
    private Status status;

}
