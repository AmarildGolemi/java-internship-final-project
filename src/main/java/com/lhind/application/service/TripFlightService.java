package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripFlightService {

    List<Flight> findAll(Long userId, Long tripId);

    Flight findById(Long userId, Long tripId, Long flightId);

    List<Flight> findAvailableFlights(Long userId, Long tripId);

    @Transactional
    Flight addFlight(Long userId, Long tripId, Long flightId);
}
