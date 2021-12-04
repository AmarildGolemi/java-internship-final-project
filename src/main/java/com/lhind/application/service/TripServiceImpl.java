package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.entity.Trip;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.TripRepository;
import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final FlightService flightService;

    private final TripRepository tripRepository;

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip findById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Trip> findAllByTripReason(TripReason tripReason) {
        List<Trip> filteredTrips = tripRepository.findAllByTripReason(tripReason);

        if (filteredTrips.isEmpty()) {
            //TODO: Log warning
        }

        return filteredTrips;
    }

    @Override
    public List<Trip> findAllByStatus(Status status) {
        List<Trip> filteredTrips = tripRepository.findAllByStatus(status);

        if (filteredTrips.isEmpty()) {
            //TODO: Log warning
        }

        return filteredTrips;
    }

    @Override
    public List<Trip> findAllByTripReasonAndStatus(TripReason tripReason, Status status) {
        List<Trip> filteredTrips = tripRepository.findAllByTripReasonAndStatus(tripReason, status);

        if (filteredTrips.isEmpty()) {
            //TODO: Log warning
        }

        return filteredTrips;
    }

    @Override
    @Transactional
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public Trip update(Long id, Trip trip) {
        validateTrip(trip);

        Optional<Trip> optionalTrip = tripRepository.findById(id);

        Trip tripToUpdate = getTrip(optionalTrip);
        trip.setId(tripToUpdate.getId());

        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public Trip patch(Long id, Trip trip) {
        validateTrip(trip);

        Optional<Trip> optionalTrip = tripRepository.findById(id);

        Trip tripToPatch = getTrip(optionalTrip);

        patchTrip(trip, tripToPatch);

        return tripRepository.save(tripToPatch);
    }

    private void validateTrip(Trip trip) {
        if (trip.getId() != null
                || trip.getStatus() != Status.CREATED) {
            throw new BadRequestException();
        }
    }

    private void patchTrip(Trip trip, Trip tripToPatch) {
        if (trip.getTripReason() != null) {
            tripToPatch.setTripReason(trip.getTripReason());
        }

        if (trip.getDescription() != null) {
            tripToPatch.setDescription(trip.getDescription());
        }

        if (trip.getFrom() != null) {
            tripToPatch.setFrom(trip.getFrom());
        }

        if (trip.getTo() != null) {
            tripToPatch.setTo(trip.getTo());
        }

        if (trip.getDepartureDate() != null) {
            tripToPatch.setDepartureDate(trip.getDepartureDate());
        }

        if (trip.getArrivalDate() != null) {
            tripToPatch.setArrivalDate(trip.getArrivalDate());
        }
    }

    @Override
    @Transactional
    public String delete(Long id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);

        Trip tripToDelete = getTrip(optionalTrip);

        tripRepository.delete(tripToDelete);

        return "Trip deleted";
    }

    @Override
    @Transactional
    public Trip approve(Long id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);

        Trip tripToApprove = getTrip(optionalTrip);
        tripToApprove.setStatus(Status.APPROVED);

        return tripRepository.save(tripToApprove);
    }

    @Override
    @Transactional
    public Trip reject(Long id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);

        Trip tripToReject = getTrip(optionalTrip);
        tripToReject.setStatus(Status.REJECTED);

        return tripRepository.save(tripToReject);
    }

    @Override
    @Transactional
    public Trip sendForApproval(Long id) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);

        Trip tripToSend = getTrip(optionalTrip);
        tripToSend.setStatus(Status.WAITING_FOR_APPROVAL);

        return tripRepository.save(tripToSend);
    }

    @Override
    @Transactional
    public Flight addFlight(Long id, Long flightId) {
        Optional<Trip> optionalTrip = tripRepository.findById(id);

        Trip tripToPatch = getTrip(optionalTrip);

        Flight flightToAdd = flightService.findById(flightId);

        tripToPatch.getFlights().add(flightToAdd);

        tripRepository.save(tripToPatch);

        return flightToAdd;
    }

    private Trip getTrip(Optional<Trip> tripOptional) {
        if (tripOptional.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return tripOptional.get();
    }

}
