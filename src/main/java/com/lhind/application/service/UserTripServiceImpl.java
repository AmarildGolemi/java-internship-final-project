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
    public Trip addTrip(Long userId, Trip tripToAdd) {
        User userToPatch = userService.findById(userId);

        checkTripAlreadyAdded(tripToAdd, userToPatch);

        userToPatch.addTrip(tripToAdd);

        userService.save(userToPatch);

        return tripToAdd;
    }

    private void checkTripAlreadyAdded(Trip tripToAdd, User userToPatch) {
        userToPatch.getTrips().stream()
                .filter(trip -> trip.equals(tripToAdd))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Trip already added"));
    }

    @Override
    @Transactional
    public Trip update(Long userId, Long tripId, Trip trip) {
        validaTripId(trip);

        User existingUser = userService.findById(userId);
        Trip tripToUpdate = existingUser.getTrips().stream()
                .filter(tripToFind -> validateTrip(tripId, tripToFind))
                .findFirst()
                .orElseThrow(BadRequestException::new);

        trip.setId(tripToUpdate.getId());
        trip.setUser(existingUser);

        return tripService.update(trip);
    }

    @Override
    @Transactional
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
            throw new BadRequestException("Id should not be provided.");
        }
    }

    @Override
    @Transactional
    public Trip sendForApproval(Long userId, Long tripId) {
        User existingUser = userService.findById(userId);

        Trip tripToSend = existingUser.getTrips().stream()
                .filter(tripToFind -> validateTrip(tripId, tripToFind))
                .findFirst()
                .orElseThrow(BadRequestException::new);

        return tripService.sendForApproval(tripToSend);
    }


    @Override
    @Transactional
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
