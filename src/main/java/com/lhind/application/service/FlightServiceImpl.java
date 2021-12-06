package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public List<Flight> findAll() {
        List<Flight> flights = flightRepository.findAll();

        if (flights.isEmpty()) {
            //TODO: Log warning.
        }

        return flights;
    }

    @Override
    public Flight findById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private Flight getFlight(Optional<Flight> flightOptional) {
        if (flightOptional.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return flightOptional.get();
    }

    @Override
    public List<Flight> findAvailableFlights(Flight flight) {
        String from = flight.getFrom();
        String to = flight.getTo();
        String departureDate = flight.getDepartureDate().toString();

        return flightRepository.getAvailableFlights(from, to, departureDate);
    }

    @Override
    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight update(Long id, Flight flight) {
        validateFlight(flight);

        Optional<Flight> flightOptional = flightRepository.findById(id);

        Flight flightToUpdate = getFlight(flightOptional);
        flight.setId(flightToUpdate.getId());

        return flightRepository.save(flight);
    }

    @Override
    public Flight patch(Long id, Flight flight) {
        validateFlight(flight);

        Optional<Flight> optionalFlight = flightRepository.findById(id);

        Flight flightToPatch = getFlight(optionalFlight);

        patchFlight(flight, flightToPatch);

        return flightRepository.save(flightToPatch);
    }

    private void validateFlight(Flight flight) {
        if (flight.getId() != null) {
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
        Optional<Flight> optionalFlight = flightRepository.findById(id);

        Flight flightToDelete = getFlight(optionalFlight);

        flightRepository.delete(flightToDelete);

        return "Flight deleted";
    }
}
