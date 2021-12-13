package com.lhind.application.controller.v1;

import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.service.UserTripService;
import com.lhind.application.utility.model.tripdto.TripFilterDto;
import com.lhind.application.utility.model.tripdto.TripPatchDto;
import com.lhind.application.utility.model.tripdto.TripRequestDto;
import com.lhind.application.utility.model.tripdto.TripResponseDto;
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

    public static final String BASE_URL = "/api/v1/users/trips";

    private final UserTripService userTripService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<TripResponseDto>> findAll() {
        log.info("Accessing endpoint {} to find all trips for logged user.", BASE_URL);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<TripResponseDto> trips = userTripService.findAll(loggedUsername);

        log.info("Returning list of trips.");

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<TripResponseDto>> findAllFilteredTrips(@Valid @RequestBody TripFilterDto tripDto) {
        log.info("Accessing endpoint {}/filter to find all trips for logged user by filter: {}.", BASE_URL, tripDto);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<TripResponseDto> trips = userTripService.findAllFilteredTrips(loggedUsername, tripDto);

        log.info("Returning list of trips.");

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TripResponseDto> findById(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/{} to find trip of logged user by id.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        TripResponseDto foundTrip = userTripService.findById(loggedUsername, tripId);

        log.info("Returning trip.");

        return new ResponseEntity<>(foundTrip, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TripResponseDto> addTrip(@Valid @RequestBody TripRequestDto tripDto) {
        log.info("Accessing endpoint {} to post new trip on logged user.", BASE_URL);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        TripResponseDto addedTrip = userTripService.addTrip(loggedUsername, tripDto);

        return new ResponseEntity<>(addedTrip, HttpStatus.CREATED);
    }

    @PutMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TripResponseDto> update(@PathVariable @Min(1) Long tripId,
                                                  @Valid @RequestBody TripRequestDto tripDto) {
        log.info("Accessing endpoint {}/{} to update trip of logged user.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        TripResponseDto updatedTrip = userTripService.update(loggedUsername, tripId, tripDto);

        return new ResponseEntity<>(updatedTrip, HttpStatus.OK);
    }

    @PatchMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TripResponseDto> patch(@PathVariable @Min(1) Long tripId,
                                                 @Valid @RequestBody TripPatchDto tripDto) {
        log.info("Accessing endpoint {}/{} to patch trip of logged user.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        TripResponseDto patchedTrip = userTripService.patch(loggedUsername, tripId, tripDto);

        return new ResponseEntity<>(patchedTrip, HttpStatus.OK);
    }

    @PostMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TripResponseDto> sendForApproval(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/{} to send trip of logged user for approval.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        TripResponseDto sentTrip = userTripService.sendForApproval(loggedUsername, tripId);

        log.info("Returning sent trip.");

        return new ResponseEntity<>(sentTrip, HttpStatus.OK);
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
