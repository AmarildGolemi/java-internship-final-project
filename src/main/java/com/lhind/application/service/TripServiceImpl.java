package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.TripRepository;
import com.lhind.application.utility.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    @Override
    public Trip findById(Long id) {
        return tripRepository.findById(id)
                .filter(trip -> trip.getStatus().equals(Status.WAITING_FOR_APPROVAL))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Trip> findAllWaitingForApproval() {
        return tripRepository.findAllByStatus(Status.WAITING_FOR_APPROVAL);
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
    public Trip approve(Long tripId) {
        Trip tripToApprove = tripRepository.findById(tripId).orElseThrow(ResourceNotFoundException::new);

        tripToApprove.setStatus(Status.APPROVED);

        return tripRepository.save(tripToApprove);
    }

    @Override
    @Transactional
    public Trip reject(Long tripId) {
        Trip tripToReject = tripRepository.findById(tripId).orElseThrow(ResourceNotFoundException::new);

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
