package com.lhind.application.utility.model.TripDto;

import com.lhind.application.utility.model.TripReason;
import com.lhind.application.utility.validation.FutureDate;
import com.lhind.application.utility.validation.Reason;
import com.lhind.application.utility.validation.TripAvailableDates;
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
@TripAvailableDates
public class TripDto {

    @Reason
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

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date departureDate;

    @FutureDate
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date arrivalDate;

}
