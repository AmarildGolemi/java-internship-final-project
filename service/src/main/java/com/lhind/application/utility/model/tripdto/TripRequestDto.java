package com.lhind.application.utility.model.tripdto;

import com.lhind.application.utility.model.TripReason;
import com.lhind.application.utility.validation.datevalidation.FutureDate;
import com.lhind.application.utility.validation.tripvalidation.Reason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "Trip Request")
public class TripRequestDto {

    @Reason
    @ApiModelProperty(notes = "Reason of the trip", example = "EVENT", required = true)
    private TripReason tripReason;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 5, max = 256)
    @ApiModelProperty(notes = "Description of the trip", example = "International Hackathon", required = true)
    private String description;

    @NotBlank(message = "Departing city is mandatory")
    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Departing city of the trip", example = "rome", required = true)
    private String from;

    @NotBlank(message = "Arrival city is mandatory")
    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Arrival city of the trip", example = "berlin", required = true)
    private String to;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(notes = "Departure date of the trip", example = "2021-12-25", required = true)
    private Date departureDate;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @ApiModelProperty(notes = "Arrival date of the trip", example = "2021-12-30", required = true)
    private Date arrivalDate;

}
