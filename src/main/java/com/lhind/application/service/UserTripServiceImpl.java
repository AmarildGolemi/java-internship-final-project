package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.entity.User;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.utility.mapper.TripMapper;
import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import com.lhind.application.utility.model.tripdto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lhind.application.utility.model.Status.APPROVED;
import static com.lhind.application.utility.model.Status.CREATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTripServiceImpl implements UserTripService {

    private final UserService userService;
    private final TripService tripService;

    @Override
    public List<TripDto> findAll(String loggedUsername) {
        log.info("Finding all trips for user with username: {}", loggedUsername);

        User foundUser = getUser(loggedUsername);
        List<Trip> trips = foundUser.getTrips();
        
        log.info("Retrieving trips.");

        return TripMapper.tripToTripDto(trips);
    }

    @Override
    public TripDto findById(String loggedUsername, Long tripId) {
        User foundUser = getUser(loggedUsername);

        Trip foundTrip = foundUser.getTrips().stream()
                .filter(trip -> Objects.equals(trip.getId(), tripId))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);

        return TripMapper.tripToTripDto(foundTrip);
    }

    @Override
    public Trip findApprovedTrip(String loggedUsername, Long tripId) {
        User foundUser = getUser(loggedUsername);

        return getApprovedTrip(tripId, foundUser);
    }

    @Override
    public List<TripDto> findAllFilteredTrips(String loggedUsername, TripFilterDto tripDto) {
        log.info("Finding all trips by filter.");

        User foundUser = getUser(loggedUsername);

        TripReason tripReason = tripDto.getTripReason();
        Status status = tripDto.getStatus();

        if (filterByTripReason(tripReason, status)) {
            log.info("Find all trips by status: {}", status);
            return findAllByTripReason(foundUser, tripReason);
        }

        if (filterByStatus(tripReason, status)) {
            log.info("Find all trips by reason: {}", tripReason);
            return findAllByStatus(foundUser, status);
        }

        return findAllByTripReasonAndStatus(foundUser, tripReason, status);
    }

    private boolean filterByStatus(TripReason tripReason, Status status) {
        return tripReason == null && status != null;
    }

    private boolean filterByTripReason(TripReason tripReason, Status status) {
        return tripReason != null && status == null;
    }

    private List<TripDto> findAllByTripReason(User foundUser, TripReason tripReason) {
        log.info("Returning all trips with reason: {}", tripReason);

        List<Trip> trips = foundUser.getTrips().stream()
                .filter(trip -> trip.getTripReason().equals(tripReason))
                .collect(Collectors.toList());
        
        return TripMapper.tripToTripDto(trips);
    }

    private List<TripDto> findAllByStatus(User foundUser, Status status) {
        log.info("Returning all trips with status: {}", status);

        List<Trip> trips = foundUser.getTrips().stream()
                .filter(trip -> trip.getStatus().equals(status))
                .collect(Collectors.toList());
        
        return TripMapper.tripToTripDto(trips);
    }

    private List<TripDto> findAllByTripReasonAndStatus(User foundUser, TripReason tripReason, Status status) {
        log.info("Returning all trips with reason: {} and status: {}", tripReason, status);

        List<Trip> trips = foundUser.getTrips().stream()
                .filter(trip -> trip.getTripReason().equals(tripReason) && trip.getStatus().equals(status))
                .collect(Collectors.toList());
        
        return TripMapper.tripToTripDto(trips);
    }

    @Override
    @Transactional
    public TripDto addTrip(String loggedUsername, TripPostDto trip) {
        log.info("Adding trip: {} to user with username: {}", trip, loggedUsername);

        User userToPatch = getUser(loggedUsername);
        Trip tripToAdd = TripMapper.tripDtoToTrip(trip);

        checkTripAlreadyAdded(tripToAdd, userToPatch);

        userToPatch.addTrip(tripToAdd);
        tripToAdd.setUser(userToPatch);

        Trip savedTrip = tripService.save(tripToAdd);
        userService.saveUserAfterAddingNewTrip(userToPatch);

        log.info("Returning saved trip: {}", savedTrip);

        return TripMapper.tripToTripDto(savedTrip);
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
    public TripDto update(String loggedUsername, Long tripId, TripUpdateDto trip) {
        log.info("Updating trip: {} of user with username: {}", tripId, loggedUsername);

        validateTripId(trip);

        User foundUser = getUser(loggedUsername);
        Trip foundTrip = getCreatedTrip(tripId, foundUser);

        Trip tripToUpdate = TripMapper.tripDtoToTrip(trip);
        tripToUpdate.setId(foundTrip.getId());
        tripToUpdate.setUser(foundUser);
        Trip updatedTrip = tripService.update(tripToUpdate);
        
        return TripMapper.tripToTripDto(updatedTrip);
    }

    @Override
    @Transactional
    public TripDto patch(String loggedUsername, Long tripId, TripPatchDto trip) {
        log.info("Patching trip: {} of user with username: {}", tripId, loggedUsername);
        
        User foundUser = getUser(loggedUsername);
        Trip foundTrip = getCreatedTrip(tripId, foundUser);
        
        Trip tripToPatch = TripMapper.tripDtoToTrip(trip);
        Trip patchedTrip = tripService.patch(foundTrip, tripToPatch);
        
        return TripMapper.tripToTripDto(patchedTrip);
    }

    private void validateTripId(TripUpdateDto trip) {
        log.info("Validating trip id is not provided.");

        if (trip.getId() != null) {
            log.error("Trip id: {} is provided.", trip.getId());

            throw new BadRequestException("Id should not be provided.");
        }
    }

    @Override
    @Transactional
    public TripDto sendForApproval(String loggedUsername, Long tripId) {
        log.info("Sending trip: {} of user with username: {} for approval.", tripId, loggedUsername);

        User foundUser = getUser(loggedUsername);
        Trip tripToSend = getCreatedTrip(tripId, foundUser);
        Trip approvedTrip = tripService.sendForApproval(tripToSend);
        
        return TripMapper.tripToTripDto(approvedTrip);
    }

    @Override
    @Transactional
    public String delete(String loggedUsername, Long tripId) {
        log.info("Deleting trip: {} of user with username: {}", tripId, loggedUsername);

        User foundUser = getUser(loggedUsername);
        Trip tripToDelete = getCreatedTrip(tripId, foundUser);

        return tripService.delete(tripToDelete);
    }

    private User getUser(String loggedUsername) {
        return userService.getByUsername(loggedUsername);
    }
    
    private Trip getCreatedTrip(Long tripId, User foundUser) {
        log.info("Getting trip: {} from user: {}", tripId, foundUser);

        return foundUser.getTrips().stream()
                .filter(tripToFind -> validateTripCreated(tripId, tripToFind))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Trip: {} of user: {} is not valid.", tripId, foundUser);

                    throw new BadRequestException();
                });
    }

    private Trip getApprovedTrip(Long tripId, User foundUser) {
        log.info("Getting trip: {} from user: {}", tripId, foundUser);

        return foundUser.getTrips().stream()
                .filter(tripToFind -> validateTripApproved(tripId, tripToFind))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Trip: {} of user: {} is not valid.", tripId, foundUser);

                    throw new BadRequestException();
                });
    }

    private boolean validateTripCreated(Long tripId, Trip tripToFind) {
        log.info("Validating trip exists and status is created.");
        return Objects.equals(tripToFind.getId(), tripId) && tripToFind.getStatus().equals(CREATED);
    }

    private boolean validateTripApproved(Long tripId, Trip tripToFind) {
        log.info("Validating trip exists and status is approved.");
        return Objects.equals(tripToFind.getId(), tripId) && tripToFind.getStatus().equals(APPROVED);
    }

}
