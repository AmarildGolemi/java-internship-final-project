package com.lhind.application.utility.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class TripDto {

    private TripReason tripReason;
    private String description;
    private String from;
    private String to;
    private Date departureDate;
    private Date arrivalDate;

    private Long userId;
}
