package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.entity.Trip;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.utility.mapper.FlightMapper;
import com.lhind.application.utility.model.flightdto.FlightFilterDto;
import com.lhind.application.utility.model.flightdto.FlightResponseDto;
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
    public List<FlightResponseDto> findAll(String loggedUsername, Long tripId) {
        log.info("Finding all flights on trip with id: {} for user: {}", tripId, loggedUsername);

        Trip foundTrip = userTripService.findApprovedTrip(loggedUsername, tripId);

        log.info("Retrieving all flights.");

        List<Flight> flights = foundTrip.getFlights();

        return FlightMapper.flightToFlightDto(flights);
    }

    @Override
    public FlightResponseDto findById(String loggedUsername, Long tripId, Long flightId) {
        log.info("Finding flight with id: {} on trip with id: {} for user: {}", flightId, tripId, loggedUsername);

        Trip foundTrip = userTripService.findApprovedTrip(loggedUsername, tripId);

        log.info("Retrieving flight on the trip.");

        Flight foundFlight = getFlight(loggedUsername, tripId, flightId, foundTrip);

        return FlightMapper.flightToFlightDto(foundFlight);
    }

    private Flight getFlight(String loggedUsername, Long tripId, Long flightId, Trip foundTrip) {
        return foundTrip.getFlights().stream()
                .filter(flight -> Objects.equals(flight.getId(), flightId))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("No flight with id: {} found in the trip with id: {} for user: {}",
                            flightId, tripId, loggedUsername);

                    throw new ResourceNotFoundException();
                });
    }

    @Override
    public List<FlightResponseDto> findDepartureFlights(String loggedUsername, Long tripId) {
        log.info("Finding suggested departure flights for trip with id: {} for user: {}", tripId, loggedUsername);

        Trip foundTrip = userTripService.findApprovedTrip(loggedUsername, tripId);

        FlightFilterDto flightToFind = new FlightFilterDto();
        flightToFind.setFrom(foundTrip.getFrom());
        flightToFind.setTo(foundTrip.getTo());
        flightToFind.setDepartureDate(foundTrip.getDepartureDate());

        return flightService.findFlights(flightToFind);
    }

    @Override
    public List<FlightResponseDto> findArrivalFlights(String loggedUsername, Long tripId) {
        log.info("Finding suggested arrival flights for trip with id: {} for user: {}", tripId, loggedUsername);

        Trip foundTrip = userTripService.findApprovedTrip(loggedUsername, tripId);

        FlightFilterDto flightToFind = new FlightFilterDto();
        flightToFind.setFrom(foundTrip.getTo());
        flightToFind.setTo(foundTrip.getFrom());
        flightToFind.setDepartureDate(foundTrip.getArrivalDate());

        return flightService.findFlights(flightToFind);
    }

    @Override
    @Transactional
    public FlightResponseDto addFlight(String loggedUsername, Long tripId, Long flightId) {
        log.info("Adding flight with id: {} on trip with id: {} for user: {}", flightId, tripId, loggedUsername);

        Trip tripToPatch = userTripService.findApprovedTrip(loggedUsername, tripId);
        Flight flightToAdd = flightService.getById(flightId);

        checkFlightAlreadyAdded(tripToPatch, flightToAdd);

        tripToPatch.getFlights().add(flightToAdd);

        tripService.save(tripToPatch);

        log.info("Returning added flight.");

        return FlightMapper.flightToFlightDto(flightToAdd);
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
