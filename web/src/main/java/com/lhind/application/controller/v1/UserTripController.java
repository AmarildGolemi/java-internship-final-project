package com.lhind.application.controller.v1;

import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.service.UserTripService;
import com.lhind.application.utility.model.tripdto.TripFilterDto;
import com.lhind.application.utility.model.tripdto.TripPatchDto;
import com.lhind.application.utility.model.tripdto.TripRequestDto;
import com.lhind.application.utility.model.tripdto.TripResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

import static com.lhind.application.controller.v1.UserTripController.BASE_URL;
import static com.lhind.application.swagger.SwaggerConstant.USER_TRIP_API_TAG;

@Slf4j
@Validated
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Api(tags = {USER_TRIP_API_TAG})
public class UserTripController {

    public static final String BASE_URL = "/api/v1/users/trips";

    private final UserTripService userTripService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Find all trips of a user", response = TripResponseDto.class)
    public ResponseEntity<List<TripResponseDto>> findAll() {
        log.info("Accessing endpoint {} to find all trips for logged user.", BASE_URL);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<TripResponseDto> trips = userTripService.findAll(loggedUsername);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Find all trips of a user by status and/or reason", response = TripResponseDto.class)
    public ResponseEntity<List<TripResponseDto>> findAllFilteredTrips(@Valid @RequestBody TripFilterDto tripDto) {
        log.info("Accessing endpoint {}/filter to find all trips for logged user by filter: {}.", BASE_URL, tripDto);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<TripResponseDto> trips = userTripService.findAllFilteredTrips(loggedUsername, tripDto);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Find all trips of a user by Id", response = TripResponseDto.class)
    public ResponseEntity<TripResponseDto> findById(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/{} to find trip of logged user by id.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        TripResponseDto foundTrip = userTripService.findById(loggedUsername, tripId);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(foundTrip, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Add a new trip for a user", response = TripResponseDto.class)
    public ResponseEntity<TripResponseDto> addTrip(@Valid @RequestBody TripRequestDto tripDto) {
        log.info("Accessing endpoint {} to post new trip on logged user.", BASE_URL);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        TripResponseDto addedTrip = userTripService.addTrip(loggedUsername, tripDto);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(addedTrip, HttpStatus.CREATED);
    }

    @PutMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Update an existing trip of a user", notes = "Trip status must be created", response = TripResponseDto.class)
    public ResponseEntity<TripResponseDto> update(@PathVariable @Min(1) Long tripId,
                                                  @Valid @RequestBody TripRequestDto tripDto) {
        log.info("Accessing endpoint {}/{} to update trip of logged user.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        TripResponseDto updatedTrip = userTripService.update(loggedUsername, tripId, tripDto);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(updatedTrip, HttpStatus.OK);
    }

    @PatchMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Patch an existing trip of a user", notes = "Trip status must be created", response = TripResponseDto.class)
    public ResponseEntity<TripResponseDto> patch(@PathVariable @Min(1) Long tripId,
                                                 @Valid @RequestBody TripPatchDto tripDto) {
        log.info("Accessing endpoint {}/{} to patch trip of logged user.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        TripResponseDto patchedTrip = userTripService.patch(loggedUsername, tripId, tripDto);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(patchedTrip, HttpStatus.OK);
    }

    @PostMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Send an existing trip of a user for approval", notes = "Trip status must be created", response = TripResponseDto.class)
    public ResponseEntity<TripResponseDto> sendForApproval(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/{} to send trip of logged user for approval.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        TripResponseDto sentTrip = userTripService.sendForApproval(loggedUsername, tripId);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(sentTrip, HttpStatus.OK);
    }

    @DeleteMapping("/{tripId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Delete an existing trip of a user", notes = "Trip status must be created", response = TripResponseDto.class)
    public ResponseEntity<String> delete(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/{} to delete trip of logged user.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        String deleteConfirmationMessage = userTripService.delete(loggedUsername, tripId);

        log.info("Returning confirmation message.");

        return new ResponseEntity<>(deleteConfirmationMessage, HttpStatus.OK);
    }

}
