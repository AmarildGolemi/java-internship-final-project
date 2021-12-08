package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripFlightService {


    List<Flight> findAll(String loggedUsername, Long tripId);

    Flight findById(String loggedUsername, Long tripId, Long flightId);

    List<Flight> findFlights(String loggedUsername, Long tripId);

    @Transactional
    Flight addFlight(String loggedUsername, Long tripId, Long flightId);
}
