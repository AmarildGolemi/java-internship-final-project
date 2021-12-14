package com.lhind.application.utility.model.tripdto;

import com.lhind.application.utility.model.TripReason;
import com.lhind.application.utility.validation.datevalidation.PatchFutureDate;
import com.lhind.application.utility.validation.tripvalidation.Reason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "Trip Patch")
public class TripPatchDto {

    @Reason
    @ApiModelProperty(notes = "Reason of the trip", example = "EVENT")
    private TripReason tripReason;

    @Size(min = 5, max = 256)
    @ApiModelProperty(notes = "Description of the trip", example = "International Hackathon")
    private String description;

    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Departing city of the trip", example = "rome")
    private String from;

    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Arrival city of the trip", example = "berlin")
    private String to;

    @PatchFutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(notes = "Departure date of the trip", example = "2021-12-25")
    private Date departureDate;

    @PatchFutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(notes = "Arrival date of the trip", example = "2021-12-30")
    private Date arrivalDate;

}
