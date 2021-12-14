package com.lhind.application.utility.model.tripdto;

import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import com.lhind.application.utility.validation.tripvalidation.Reason;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(value = "Trip Filter")
public class TripFilterDto {

    @Reason
    @ApiModelProperty(notes = "Reason of the trip", example = "EVENT")
    private TripReason tripReason;

    @com.lhind.application.utility.validation.tripvalidation.Status
    @ApiModelProperty(notes = "Status of the trip", example = "CREATED")
    private Status status;

}
