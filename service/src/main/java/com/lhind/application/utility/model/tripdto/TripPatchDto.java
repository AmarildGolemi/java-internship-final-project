package com.lhind.application.utility.model.tripdto;

import com.lhind.application.utility.model.TripReason;
import com.lhind.application.utility.validation.datevalidation.PatchFutureDate;
import com.lhind.application.utility.validation.tripvalidation.Reason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class TripPatchDto {

    @Reason
    private TripReason tripReason;

    @Size(min = 5, max = 256)
    private String description;

    @Size(min = 3, max = 20)
    private String from;

    @Size(min = 3, max = 20)
    private String to;

    @PatchFutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date departureDate;

    @PatchFutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date arrivalDate;

}
