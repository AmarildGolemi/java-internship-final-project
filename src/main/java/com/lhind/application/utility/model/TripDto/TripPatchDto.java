package com.lhind.application.utility.model.TripDto;

import com.lhind.application.utility.model.TripReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class TripPatchDto {

    private TripReason tripReason;

    @Size(min = 5, max = 256)
    private String description;

    @Size(min = 3, max = 20)
    private String from;

    @Size(min = 3, max = 20)
    private String to;

    private Date departureDate;

    private Date arrivalDate;

}
