package com.lhind.application.utility.model.tripdto;

import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import com.lhind.application.utility.validation.tripvalidation.Reason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TripFilterDto {

    @Reason
    private TripReason tripReason;

    @com.lhind.application.utility.validation.tripvalidation.Status
    private Status status;

}
