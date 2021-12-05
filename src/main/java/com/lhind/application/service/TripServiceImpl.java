package com.lhind.application.service;

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

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    @Override
    public List<Trip> findAll() {
        List<Trip> trips = tripRepository.findAll();

        if (trips.isEmpty()) {
            //TODO: Log warning.
        }

        return trips;
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
    public Trip update(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public Trip patch(Trip tripToPatch, Trip trip) {
        patchTrip(tripToPatch, trip);

        return tripRepository.save(tripToPatch);
    }

    private void validateTrip(Trip trip) {
        if (trip.getId() != null
                || trip.getStatus() != Status.CREATED) {
            throw new BadRequestException();
        }
    }

    private void patchTrip(Trip tripToPatch, Trip trip) {
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
    public Trip sendForApproval(Trip tripToSend) {
        tripToSend.setStatus(Status.WAITING_FOR_APPROVAL);

        return tripRepository.save(tripToSend);
    }

    @Override
    @Transactional
    public Trip approve(Trip tripToApprove) {
        tripToApprove.setStatus(Status.APPROVED);

        return tripRepository.save(tripToApprove);
    }

    @Override
    @Transactional
    public Trip reject(Trip tripToReject) {
        tripToReject.setStatus(Status.REJECTED);

        return tripRepository.save(tripToReject);
    }

    @Override
    @Transactional
    public String delete(Trip tripToDelete) {
        tripRepository.delete(tripToDelete);

        return "Trip deleted";
    }

}
