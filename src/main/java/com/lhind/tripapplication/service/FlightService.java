package com.lhind.tripapplication.service;

import com.lhind.tripapplication.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
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
