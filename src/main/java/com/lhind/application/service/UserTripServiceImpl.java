package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.entity.User;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import com.lhind.application.utility.model.tripdto.TripFilterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTripServiceImpl implements UserTripService {

    private final UserService userService;
    private final TripService tripService;

    @Override
    public List<Trip> findAll(String loggedUsername) {
        log.info("Finding all trips for user with username: {}", loggedUsername);

        User existingUser = getUser(loggedUsername);

        log.info("Retrieving trips.");

        return existingUser.getTrips();
    }

    @Override
    public Trip findById(String loggedUsername, Long tripId) {
        User existingUser = getUser(loggedUsername);

        return existingUser.getTrips().stream()
                .filter(trip -> Objects.equals(trip.getId(), tripId))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Trip> findAllFilteredTrips(String loggedUsername, TripFilterDto tripDto) {
        log.info("Finding all trips by filter.");

        User existingUser = getUser(loggedUsername);

        TripReason tripReason = tripDto.getTripReason();
        Status status = tripDto.getStatus();

        if (filterByTripReason(tripReason, status)) {
            log.info("Find all trips by status: {}", status);
            return findAllByTripReason(existingUser, tripReason);
        }

        if (filterByStatus(tripReason, status)) {
            log.info("Find all trips by reason: {}", tripReason);
            return findAllByStatus(existingUser, status);
        }

        return findAllByTripReasonAndStatus(existingUser, tripReason, status);
    }

    private boolean filterByStatus(TripReason tripReason, Status status) {
        return tripReason == null && status != null;
    }

    private boolean filterByTripReason(TripReason tripReason, Status status) {
        return tripReason != null && status == null;
    }

    private List<Trip> findAllByTripReason(User existingUser, TripReason tripReason) {
        log.info("Returning all trips with reason: {}", tripReason);

        return existingUser.getTrips().stream()
                .filter(trip -> trip.getTripReason().equals(tripReason))
                .collect(Collectors.toList());
    }

    private List<Trip> findAllByStatus(User existingUser, Status status) {
        log.info("Returning all trips with status: {}", status);

        return existingUser.getTrips().stream()
                .filter(trip -> trip.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    private List<Trip> findAllByTripReasonAndStatus(User existingUser, TripReason tripReason, Status status) {
        log.info("Returning all trips with reason: {} and status: {}", tripReason, status);

        return existingUser.getTrips().stream()
                .filter(trip -> trip.getTripReason().equals(tripReason) && trip.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Trip addTrip(String loggedUsername, Trip tripToAdd) {
        log.info("Adding trip: {} to user with username: {}", tripToAdd, loggedUsername);

        User userToPatch = getUser(loggedUsername);

        checkTripAlreadyAdded(tripToAdd, userToPatch);

        userToPatch.addTrip(tripToAdd);
        tripToAdd.setUser(userToPatch);

        Trip savedTrip = tripService.save(tripToAdd);
        userService.saveUserAfterAddingNewTrip(userToPatch);

        log.info("Returning saved trip: {}", savedTrip);

        return savedTrip;
    }

    private void checkTripAlreadyAdded(Trip tripToAdd, User userToPatch) {
        log.info("Checking if trip already exists.");

        Optional<Trip> existingTrip = userToPatch.getTrips().stream()
                .filter(tripToAdd::equals)
                .findFirst();

        if (existingTrip.isPresent()) {
            log.error("Trip: {} already exists.", tripToAdd);
            throw new BadRequestException("Trip already exists.");
        }
    }

    @Override
    @Transactional
    public Trip update(String loggedUsername, Long tripId, Trip trip) {
        log.info("Updating trip: {} of user with username: {}", tripId, loggedUsername);

        validateTripId(trip);

        User existingUser = getUser(loggedUsername);

        Trip tripToUpdate = getTrip(tripId, existingUser);

        trip.setId(tripToUpdate.getId());
        trip.setUser(existingUser);

        return tripService.update(trip);
    }

    @Override
    @Transactional
    public Trip patch(String loggedUsername, Long tripId, Trip trip) {
        log.info("Patching trip: {} of user with username: {}", tripId, loggedUsername);

        validateTripId(trip);

        User existingUser = getUser(loggedUsername);

        Trip tripToPatch = getTrip(tripId, existingUser);

        return tripService.patch(tripToPatch, trip);
    }

    private void validateTripId(Trip trip) {
        log.info("Validating trip id is not provided.");

        if (trip.getId() != null) {
            log.error("Trip id: {} is provided.", trip.getId());

            throw new BadRequestException("Id should not be provided.");
        }
    }

    @Override
    @Transactional
    public Trip sendForApproval(String loggedUsername, Long tripId) {
        log.info("Sending trip: {} of user with username: {} for approval.", tripId, loggedUsername);

        User existingUser = getUser(loggedUsername);
        Trip tripToSend = getTrip(tripId, existingUser);

        return tripService.sendForApproval(tripToSend);
    }

    @Override
    @Transactional
    public String delete(String loggedUsername, Long tripId) {
        log.info("Deleting trip: {} of user with username: {}", tripId, loggedUsername);

        User existingUser = getUser(loggedUsername);
        Trip tripToDelete = getTrip(tripId, existingUser);

        return tripService.delete(tripToDelete);
    }

    private User getUser(String loggedUsername) {
        return userService.findByUsername(loggedUsername);
    }


    private Trip getTrip(Long tripId, User existingUser) {
        log.info("Getting trip: {} from user: {}", tripId, existingUser);

        return existingUser.getTrips().stream()
                .filter(tripToFind -> validateTrip(tripId, tripToFind))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Trip: {} of user: {} is not valid.", tripId, existingUser);

                    throw new BadRequestException();
                });
    }

    private boolean validateTrip(Long tripId, Trip tripToFind) {
        log.info("Validating trip.");
        return Objects.equals(tripToFind.getId(), tripId) && tripToFind.getStatus().equals(Status.CREATED);
    }

}
