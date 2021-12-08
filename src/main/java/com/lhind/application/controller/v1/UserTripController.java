package com.lhind.application.controller.v1;

import com.lhind.application.entity.Trip;
import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.service.UserTripService;
import com.lhind.application.utility.mapper.TripMapper;
import com.lhind.application.utility.model.tripdto.TripCreateDto;
import com.lhind.application.utility.model.tripdto.TripDto;
import com.lhind.application.utility.model.tripdto.TripFilterDto;
import com.lhind.application.utility.model.tripdto.TripPatchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(UserTripController.BASE_URL)
@RequiredArgsConstructor
public class UserTripController {

    public static final String BASE_URL = "api/v1/users/trips";

    private final UserTripService userTripService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<TripDto>> findAll() {
        log.info("Accessing endpoint {} to find all trips for logged user.", BASE_URL);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<TripDto> trips = TripMapper.tripToTripDto(
                userTripService.findAll(loggedUsername)
        );

        log.info("Returning list of trips.");

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<TripDto>> findAllFilteredTrips(@Valid @RequestBody TripFilterDto tripDto) {
        log.info("Accessing endpoint {}/filter to find all trips for logged user by filter: {}.", BASE_URL, tripDto);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<TripDto> trips = TripMapper.tripToTripDto(
                userTripService.findAllFilteredTrips(loggedUsername, tripDto)
        );

        log.info("Returning list of trips.");

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TripDto> findById(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/{} to find trip of logged user by id.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        Trip existingTrip = userTripService.findById(loggedUsername, tripId);

        log.info("Returning trip.");

        return new ResponseEntity<>(TripMapper.tripToTripDto(existingTrip), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TripDto> addTrip(@Valid @RequestBody TripCreateDto tripDto) {
        log.info("Accessing endpoint {} to post new trip on logged user.", BASE_URL);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        Trip tripToAdd = TripMapper.tripDtoToTrip(tripDto);
        Trip addedTrip = userTripService.addTrip(loggedUsername, tripToAdd);

        log.info("Returning added trip.");

        return new ResponseEntity<>(TripMapper.tripToTripDto(addedTrip), HttpStatus.CREATED);
    }

    @PutMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TripDto> update(@PathVariable @Min(1) Long tripId,
                                          @Valid @RequestBody TripCreateDto tripDto) {
        log.info("Accessing endpoint {}/{} to update trip of logged user.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        Trip tripToUpdate = TripMapper.tripDtoToTrip(tripDto);
        Trip addedTrip = userTripService.update(loggedUsername, tripId, tripToUpdate);

        log.info("Returning updated trip.");

        return new ResponseEntity<>(TripMapper.tripToTripDto(addedTrip), HttpStatus.OK);
    }

    @PatchMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TripDto> patch(@PathVariable @Min(1) Long tripId,
                                         @Valid @RequestBody TripPatchDto tripDto) {
        log.info("Accessing endpoint {}/{} to patch trip of logged user.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        Trip tripToPatch = TripMapper.tripDtoToTrip(tripDto);
        Trip patchedTrip = userTripService.patch(loggedUsername, tripId, tripToPatch);

        log.info("Returning patched trip.");

        return new ResponseEntity<>(TripMapper.tripToTripDto(patchedTrip), HttpStatus.OK);
    }

    @PostMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TripDto> sendForApproval(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/{} to send trip of logged user for approval.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        Trip sentTrip = userTripService.sendForApproval(loggedUsername, tripId);

        log.info("Returning sent trip.");

        return new ResponseEntity<>(TripMapper.tripToTripDto(sentTrip), HttpStatus.OK);
    }

    @DeleteMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> delete(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/{} to delete trip of logged user.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        log.info("Returning confirmation message.");

        return new ResponseEntity<>(userTripService.delete(loggedUsername, tripId), HttpStatus.OK);
    }

}
