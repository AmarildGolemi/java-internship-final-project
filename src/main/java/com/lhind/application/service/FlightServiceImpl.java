package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public List<Flight> findAll() {
        log.info("Finding all flights.");

        List<Flight> flights = flightRepository.findAll();

        if (flights.isEmpty()) {
            log.warn("Zero flights retrieved from the database.");
        }

        log.info("Returning the list of flights.");

        return flights;
    }

    @Override
    public Flight findById(Long id) {
        log.info("Finding flight by flight id: {}.", id);

        return flightRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Found no flights with id: {}", id);

                    throw new ResourceNotFoundException();
                });
    }

    @Override
    public List<Flight> findFlights(Flight flight) {
        String from = flight.getFrom();
        String to = flight.getTo();
        String departureDate = flight.getDepartureDate().toString();

        log.info("Retrieving flights from {} to {} on date {}.", from, to, departureDate);

        return flightRepository.findFlights(from, to, departureDate);
    }

    @Override
    public Flight save(Flight flight) {
        log.info("Saving flight: {}", flight);

        return flightRepository.save(flight);
    }

    @Override
    public Flight update(Long id, Flight flight) {
        log.info("Updating flight with id: {}", id);

        validateFlight(flight);

        Optional<Flight> flightOptional = flightRepository.findById(id);

        Flight flightToUpdate = getFlight(flightOptional);
        flight.setId(flightToUpdate.getId());

        log.info("Saving updated flight.");

        return flightRepository.save(flight);
    }

    @Override
    public Flight patch(Long id, Flight flight) {
        log.info("Patching flight with id: {}", id);

        validateFlight(flight);

        Optional<Flight> optionalFlight = flightRepository.findById(id);

        Flight flightToPatch = getFlight(optionalFlight);

        patchFlight(flight, flightToPatch);

        log.info("Saving updated flight.");

        return flightRepository.save(flightToPatch);
    }

    private void validateFlight(Flight flight) {
        log.info("Validating flight id is not provided.");

        if (flight.getId() != null) {
            log.error("Flight id is provided: {}.", flight.getId());

            throw new BadRequestException("Id should not be provided.");
        }
    }

    private void patchFlight(Flight flight, Flight flightToPatch) {
        if (flight.getFrom() != null) {
            flightToPatch.setFrom(flight.getFrom());
        }

        if (flight.getTo() != null) {
            flightToPatch.setTo(flight.getTo());
        }

        if (flight.getDepartureDate() != null) {
            flightToPatch.setDepartureDate(flight.getDepartureDate());
        }

        if (flight.getArrivalDate() != null) {
            flightToPatch.setArrivalDate(flight.getArrivalDate());
        }
    }

    @Override
    public String delete(Long id) {
        log.info("Deleting flight with id: {}", id);

        Optional<Flight> optionalFlight = flightRepository.findById(id);

        Flight flightToDelete = getFlight(optionalFlight);

        flightRepository.delete(flightToDelete);

        log.info("Deleting flight with id: {}.", id);

        return "Flight deleted";
    }

    private Flight getFlight(Optional<Flight> flightOptional) {
        log.info("Retrieving flight.");

        if (flightOptional.isEmpty()) {
            log.error("No flight found in the database.");

            throw new ResourceNotFoundException();
        }

        log.info("Flight found in the database.");

        return flightOptional.get();
    }
}
