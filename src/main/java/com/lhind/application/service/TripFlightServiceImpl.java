package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.entity.Trip;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.utility.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TripFlightServiceImpl implements TripFlightService {

    private final UserTripService userTripService;
    private final TripService tripService;
    private final FlightService flightService;

    @Override
    public List<Flight> findAll(Long userId, Long tripId) {
        Trip existingTrip = userTripService.findById(userId, tripId);

        return existingTrip.getFlights();
    }

    @Override
    public Flight findById(Long userId, Long tripId, Long flightId) {
        Trip existingTrip = userTripService.findById(userId, tripId);

        return existingTrip.getFlights().stream()
                .filter(flight -> Objects.equals(flight.getId(), flightId))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Flight> findAvailableFlights(Long userId, Long tripId) {
        Trip existingTrip = getTrip(userId, tripId);

        Flight flightToFind = new Flight();
        flightToFind.setFrom(existingTrip.getFrom());
        flightToFind.setTo(existingTrip.getTo());
        flightToFind.setDepartureDate(existingTrip.getDepartureDate());

        return flightService.findAvailableFlights(flightToFind);
    }

    @Override
    @Transactional
    public Flight addFlight(Long userId, Long tripId, Long flightId) {
        Trip tripToPatch = getTrip(userId, tripId);

        Flight flightToAdd = flightService.findById(flightId);
        tripToPatch.getFlights().add(flightToAdd);

        tripService.save(tripToPatch);

        return flightToAdd;
    }

    private Trip getTrip(Long userId, Long tripId) {
        Trip tripToPatch = userTripService.findById(userId, tripId);

        if (tripToPatch.getStatus() != Status.APPROVED) {
            throw new BadRequestException();
        }

        return tripToPatch;
    }

}
