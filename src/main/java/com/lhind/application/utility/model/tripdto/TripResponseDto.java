package com.lhind.application.utility.model.tripdto;

import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class TripResponseDto {

    private Long id;
    private TripReason tripReason;
    private String description;
    private String from;
    private String to;
    private Date departureDate;
    private Date arrivalDate;
    private Status status;

}
