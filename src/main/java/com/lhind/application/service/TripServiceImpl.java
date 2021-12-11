package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.TripRepository;
import com.lhind.application.utility.mapper.TripMapper;
import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.tripdto.TripDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    @Override
    public TripDto findById(Long id) {
        log.info("Finding waiting for approval trip with id: {}", id);

        Trip foundTrip = getTrip(id);

        log.info("Returning trip.");

        return TripMapper.tripToTripDto(foundTrip);
    }

    @Override
    public List<TripDto> findAllWaitingForApproval() {
        log.info("Finding all trips waiting for approval.");

        List<Trip> foundTrips = tripRepository.findAllByStatus(Status.WAITING_FOR_APPROVAL);

        log.info("Returning list of trips.");

        return TripMapper.tripToTripDto(foundTrips);
    }

    @Override
    @Transactional
    public Trip save(Trip trip) {
        log.info("Saving trip.");

        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public Trip update(Trip trip) {
        log.info("Updating trip.");

        return tripRepository.save(trip);
    }

    @Override
    @Transactional
    public Trip patch(Trip tripToPatch, Trip trip) {
        log.info("Patching trip.");

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
        log.info("Sending trip: {} for approval.", tripToSend);

        tripToSend.setStatus(Status.WAITING_FOR_APPROVAL);

        return tripRepository.save(tripToSend);
    }

    @Override
    @Transactional
    public TripDto approve(Long tripId) {
        log.info("Approving trip with id: {}", tripId);

        Trip tripToApprove = getTrip(tripId);
        tripToApprove.setStatus(Status.APPROVED);
        Trip approvedTrip = tripRepository.save(tripToApprove);

        return TripMapper.tripToTripDto(approvedTrip);
    }

    @Override
    @Transactional
    public TripDto reject(Long tripId) {
        log.info("Rejecting trip with id: {}", tripId);

        Trip tripToReject = getTrip(tripId);

        tripToReject.setStatus(Status.REJECTED);
        Trip rejectedTrip = tripRepository.save(tripToReject);

        return TripMapper.tripToTripDto(rejectedTrip);
    }

    private Trip getTrip(Long id) {
        return tripRepository.findById(id)
                .filter(trip -> trip.getStatus().equals(Status.WAITING_FOR_APPROVAL))
                .orElseThrow(() -> {
                    log.error("Trip with id: {} not found.", id);

                    throw new ResourceNotFoundException();
                });
    }

    @Override
    @Transactional
    public String delete(Trip tripToDelete) {
        log.info("Deleting trip: {}", tripToDelete);

        tripRepository.delete(tripToDelete);

        return "Trip deleted";
    }

}
