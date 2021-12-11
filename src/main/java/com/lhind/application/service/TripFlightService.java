package com.lhind.application.service;

import com.lhind.application.utility.model.flightdto.FlightResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripFlightService {


    List<FlightResponseDto> findAll(String loggedUsername, Long tripId);

    FlightResponseDto findById(String loggedUsername, Long tripId, Long flightId);

    List<FlightResponseDto> findFlights(String loggedUsername, Long tripId);

    @Transactional
    FlightResponseDto addFlight(String loggedUsername, Long tripId, Long flightId);
}
