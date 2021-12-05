package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.entity.User;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTripServiceImpl implements UserTripService {

    private final UserService userService;
    private final TripService tripService;

    @Override
    public List<Trip> findAll(Long userId) {
        User existingUser = userService.findById(userId);

        return existingUser.getTrips();
    }

    @Override
    public Trip findById(Long userId, Long tripId) {
        User existingUser = userService.findById(userId);

        return existingUser.getTrips().stream()
                .filter(trip -> Objects.equals(trip.getId(), tripId))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Trip> findAllByTripReason(Long userId, TripReason tripReason) {
        User existingUser = userService.findById(userId);

        return existingUser.getTrips().stream()
                .filter(trip -> trip.getTripReason().equals(tripReason))
                .collect(Collectors.toList());
    }

    @Override
    public List<Trip> findAllByStatus(Long userId, Status status) {
        User existingUser = userService.findById(userId);

        return existingUser.getTrips().stream()
                .filter(trip -> trip.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Trip> findAllByTripReasonAndStatus(Long userId, TripReason tripReason, Status status) {
        User existingUser = userService.findById(userId);

        return existingUser.getTrips().stream()
                .filter(trip -> trip.getTripReason().equals(tripReason) && trip.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Trip addTrip(Long userId, Trip trip) {
        User userToPatch = userService.findById(userId);

        Trip tripToAdd = tripService.save(trip);
        userToPatch.getTrips().add(tripToAdd);

        userService.save(userToPatch);

        return tripToAdd;
    }

    @Override
    public Trip update(Long userId, Long tripId, Trip trip) {
        validaTripId(trip);

        User existingUser = userService.findById(userId);
        Trip tripToUpdate = existingUser.getTrips().stream()
                .filter(tripToFind -> validateTrip(tripId, tripToFind))
                .findFirst()
                .orElseThrow(BadRequestException::new);

        trip.setId(tripToUpdate.getId());

        return tripService.update(trip);
    }

    @Override
    public Trip patch(Long userId, Long tripId, Trip trip) {
        validaTripId(trip);

        User existingUser = userService.findById(userId);
        Trip tripToPatch = existingUser.getTrips().stream()
                .filter(tripToFind -> validateTrip(tripId, tripToFind))
                .findFirst()
                .orElseThrow(BadRequestException::new);

        return tripService.patch(tripToPatch, trip);
    }

    private void validaTripId(Trip trip) {
        if (trip.getId() != null) {
            throw new BadRequestException();
        }
    }

    @Override
    public Trip sendForApproval(Long userId, Long tripId) {
        User existingUser = userService.findById(userId);

        Trip tripToSend = existingUser.getTrips().stream()
                .filter(tripToFind -> validateTrip(tripId, tripToFind))
                .findFirst()
                .orElseThrow(BadRequestException::new);

        return tripService.sendForApproval(tripToSend);
    }

    @Override
    public Trip approve(Long userId, Long tripId) {
        User existingUser = userService.findById(userId);

        Trip tripToApprove = existingUser.getTrips().stream()
                .filter(tripToFind -> validateTrip(tripId, tripToFind))
                .findFirst()
                .orElseThrow(BadRequestException::new);

        return tripService.approve(tripToApprove);
    }

    @Override
    public Trip reject(Long userId, Long tripId) {
        User existingUser = userService.findById(userId);

        Trip tripToReject = existingUser.getTrips().stream()
                .filter(tripToFind -> validateTrip(tripId, tripToFind))
                .findFirst()
                .orElseThrow(BadRequestException::new);

        return tripService.reject(tripToReject);
    }

    @Override
    public String delete(Long userId, Long tripId) {
        User existingUser = userService.findById(userId);

        Trip tripToDelete = existingUser.getTrips().stream()
                .filter(tripToFind -> validateTrip(tripId, tripToFind))
                .findFirst()
                .orElseThrow(BadRequestException::new);

        return tripService.delete(tripToDelete);
    }

    private boolean validateTrip(Long tripId, Trip tripToFind) {
        return Objects.equals(tripToFind.getId(), tripId) && tripToFind.getStatus().equals(Status.CREATED);
    }

}
