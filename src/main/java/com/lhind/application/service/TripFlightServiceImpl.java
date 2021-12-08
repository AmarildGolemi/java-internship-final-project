package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.entity.Trip;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.utility.model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripFlightServiceImpl implements TripFlightService {

    private final UserTripService userTripService;
    private final TripService tripService;
    private final FlightService flightService;

    @Override
    public List<Flight> findAll(String loggedUsername, Long tripId) {
        log.info("Finding all flights on trip with id: {} for user: {}", tripId, loggedUsername);

        Trip existingTrip = userTripService.findById(loggedUsername, tripId);

        log.info("Retrieving all flights.");

        return existingTrip.getFlights();
    }

    @Override
    public Flight findById(String loggedUsername, Long tripId, Long flightId) {
        log.info("Finding flight with id: {} on trip with id: {} for user: {}", flightId, tripId, loggedUsername);

        Trip existingTrip = userTripService.findById(loggedUsername, tripId);

        log.info("Retrieving flight on the trip.");

        return existingTrip.getFlights().stream()
                .filter(flight -> Objects.equals(flight.getId(), flightId))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("No flight with id: {} found in the trip with id: {} for user: {}", flightId, tripId, loggedUsername);

                    throw new ResourceNotFoundException();
                });
    }

    @Override
    public List<Flight> findFlights(String loggedUsername, Long tripId) {
        log.info("Finding suggested flights for trip with id: {} for user: {}", tripId, loggedUsername);

        Trip existingTrip = getTrip(loggedUsername, tripId);

        Flight flightToFind = new Flight();
        flightToFind.setFrom(existingTrip.getFrom());
        flightToFind.setTo(existingTrip.getTo());
        flightToFind.setDepartureDate(existingTrip.getDepartureDate());

        return flightService.findFlights(flightToFind);
    }

    @Override
    @Transactional
    public Flight addFlight(String loggedUsername, Long tripId, Long flightId) {
        log.info("Adding flight with id: {} on trip with id: {} for user: {}", flightId, tripId, loggedUsername);

        Trip tripToPatch = getTrip(loggedUsername, tripId);
        Flight flightToAdd = flightService.findById(flightId);

        checkFlightAlreadyAdded(tripToPatch, flightToAdd);

        tripToPatch.getFlights().add(flightToAdd);

        tripService.save(tripToPatch);

        log.info("Returning added flight.");

        return flightToAdd;
    }

    private Trip getTrip(String loggedUsername, Long tripId) {
        log.info("Finding trip with id: {} for user: {}.", tripId, loggedUsername);

        Trip tripToPatch = userTripService.findById(loggedUsername, tripId);

        if (tripToPatch.getStatus() != Status.APPROVED) {
            log.error("This trip is not approved by the admin.");

            throw new BadRequestException("This trip is not approved by the admin.");
        }

        log.info("Retrieving trip.");

        return tripToPatch;
    }

    private void checkFlightAlreadyAdded(Trip tripToPatch, Flight flightToPatch) {
        log.info("Checking if flight is already added in the trip.");

        Optional<Flight> existingFlight = tripToPatch.getFlights().stream()
                .filter(flightToPatch::equals)
                .findFirst();

        if (existingFlight.isPresent()) {
            log.error("Flight already added to trip.");

            throw new BadRequestException("Flight already added to trip.");
        }
    }

}
