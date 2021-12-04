package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FlightService {
    List<Flight> findAll();

    Flight findById(Long id);

    List<Flight> getAvailableFlights(Flight flight);

    @Transactional
    Flight save(Flight flight);

    @Transactional
    Flight update(Long id, Flight flight);

    @Transactional
    Flight patch(Long id, Flight flight);

    @Transactional
    String delete(Long id);

}
