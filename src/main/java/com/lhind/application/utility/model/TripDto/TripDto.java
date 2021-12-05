package com.lhind.application.utility.model.TripDto;

import com.lhind.application.utility.model.TripReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class TripDto {

    private TripReason tripReason;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 5, max = 256)
    private String description;

    @NotBlank(message = "Departing city is mandatory")
    @Size(min = 3, max = 20)
    private String from;

    @NotBlank(message = "Arrival city is mandatory")
    @Size(min = 3, max = 20)
    private String to;

    private Date departureDate;

    private Date arrivalDate;

}
