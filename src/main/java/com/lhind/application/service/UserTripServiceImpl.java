package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.entity.User;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.InvalidDateTimeException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.utility.mapper.TripMapper;
import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import com.lhind.application.utility.model.tripdto.TripFilterDto;
import com.lhind.application.utility.model.tripdto.TripPatchDto;
import com.lhind.application.utility.model.tripdto.TripRequestDto;
import com.lhind.application.utility.model.tripdto.TripResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.sql.Date;
import java.time.Period;
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
    public List<TripResponseDto> findAll(String loggedUsername) {
        log.info("Finding all trips for user with username: {}", loggedUsername);

        User foundUser = getUser(loggedUsername);
        List<Trip> trips = foundUser.getTrips();

        log.info("Retrieving trips.");

        return TripMapper.tripToTripDto(trips);
    }

    @Override
    public TripResponseDto findById(String loggedUsername, Long tripId) {
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
    public List<TripResponseDto> findAllFilteredTrips(String loggedUsername, TripFilterDto tripDto) {
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

    private List<TripResponseDto> findAllByTripReason(User foundUser, TripReason tripReason) {
        log.info("Returning all trips with reason: {}", tripReason);

        List<Trip> trips = foundUser.getTrips().stream()
                .filter(trip -> trip.getTripReason().equals(tripReason))
                .collect(Collectors.toList());

        return TripMapper.tripToTripDto(trips);
    }

    private List<TripResponseDto> findAllByStatus(User foundUser, Status status) {
        log.info("Returning all trips with status: {}", status);

        List<Trip> trips = foundUser.getTrips().stream()
                .filter(trip -> trip.getStatus().equals(status))
                .collect(Collectors.toList());

        return TripMapper.tripToTripDto(trips);
    }

    private List<TripResponseDto> findAllByTripReasonAndStatus(User foundUser, TripReason tripReason, Status status) {
        log.info("Returning all trips with reason: {} and status: {}", tripReason, status);

        List<Trip> trips = foundUser.getTrips().stream()
                .filter(trip -> trip.getTripReason().equals(tripReason) && trip.getStatus().equals(status))
                .collect(Collectors.toList());

        return TripMapper.tripToTripDto(trips);
    }

    @Override
    @Transactional
    public TripResponseDto addTrip(String loggedUsername, TripRequestDto trip) {
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
    public TripResponseDto update(String loggedUsername, Long tripId, TripRequestDto trip) {
        log.info("Updating trip: {} of user with username: {}", tripId, loggedUsername);

        validateTripRequestDate(trip);

        User foundUser = getUser(loggedUsername);
        Trip foundTrip = getCreatedTrip(tripId, foundUser);

        Trip tripToUpdate = getTripToUpdate(trip, foundTrip);
        tripToUpdate.setUser(foundUser);

        Trip updatedTrip = tripService.update(tripToUpdate);

        return TripMapper.tripToTripDto(updatedTrip);
    }

    private void validateTripRequestDate(TripRequestDto tripDto) {
        log.info("Validating trips's request dates are valid.");

        Date departureDate = tripDto.getDepartureDate();
        Date arrivalDate = tripDto.getArrivalDate();

        validateDatesAreProvided(departureDate, arrivalDate);

        validateTripDates(departureDate, arrivalDate);
    }

    private void validateDatesAreProvided(Date departureDate, Date arrivalDate) {
        log.info("Validating both dates are provided.");

        if (departureDate == null || arrivalDate == null) {
            log.error("Arrival and departure dates are not provided.");

            throw new InvalidDateTimeException();
        }
    }

    private Trip getTripToUpdate(TripRequestDto trip, Trip foundTrip) {
        Trip tripToUpdate = TripMapper.tripDtoToTrip(trip);
        tripToUpdate.setId(foundTrip.getId());

        return tripToUpdate;
    }

    @Override
    @Transactional
    public TripResponseDto patch(String loggedUsername, Long tripId, TripPatchDto trip) {
        log.info("Patching trip: {} of user with username: {}", tripId, loggedUsername);

        validateTripPatchDate(trip);

        User foundUser = getUser(loggedUsername);
        Trip foundTrip = getCreatedTrip(tripId, foundUser);

        Trip tripToPatch = TripMapper.tripDtoToTrip(trip);
        Trip patchedTrip = tripService.patch(foundTrip, tripToPatch);

        return TripMapper.tripToTripDto(patchedTrip);
    }

    private void validateTripPatchDate(TripPatchDto tripDto) {
        log.info("Validating trips's patch dates are valid.");

        Date departureDate = tripDto.getDepartureDate();
        Date arrivalDate = tripDto.getArrivalDate();

        validateTripDates(departureDate, arrivalDate);

        validateOneDateIsProvided(departureDate, arrivalDate);

    }

    private void validateTripDates(Date departureDate, Date arrivalDate) {
        if (departureDate != null && arrivalDate != null) {
            Period dateDifference = Period.between(
                    departureDate.toLocalDate(), arrivalDate.toLocalDate());

            if (dateDifference.getDays() < 0) {
                log.error("Arrival and departure dates are not valid.");

                throw new InvalidDateTimeException();
            }
        }
    }

    private void validateOneDateIsProvided(Date departureDate, Date arrivalDate) {
        if ((departureDate != null && arrivalDate == null)
                || (departureDate == null && arrivalDate != null)) {
            log.error("One of the dates in not provided to patch.");

            throw new ValidationException("Both date should be provided.");
        }
    }

    @Override
    @Transactional
    public TripResponseDto sendForApproval(String loggedUsername, Long tripId) {
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

                    throw new BadRequestException("Trip status is not created.");
                });
    }

    private Trip getApprovedTrip(Long tripId, User foundUser) {
        log.info("Getting trip: {} from user: {}", tripId, foundUser);

        return foundUser.getTrips().stream()
                .filter(tripToFind -> validateTripApproved(tripId, tripToFind))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Trip: {} of user: {} is not valid.", tripId, foundUser);

                    throw new BadRequestException("Trip is not approved by the admin.");
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
