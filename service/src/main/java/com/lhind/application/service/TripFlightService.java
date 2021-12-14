package com.lhind.application.service;

import com.lhind.application.utility.model.flightdto.FlightResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripFlightService {


    List<FlightResponseDto> findAll(String loggedUsername, Long tripId);

    FlightResponseDto findById(String loggedUsername, Long tripId, Long flightId);

    List<FlightResponseDto> findDepartureFlights(String loggedUsername, Long tripId);

    List<FlightResponseDto> findArrivalFlights(String loggedUsername, Long tripId);

    @Transactional
    FlightResponseDto bookFlight(String loggedUsername, Long tripId, Long flightId);
}
