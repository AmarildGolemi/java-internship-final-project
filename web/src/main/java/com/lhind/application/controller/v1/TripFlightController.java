package com.lhind.application.controller.v1;

import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.service.TripFlightService;
import com.lhind.application.swagger.SwaggerConstant;
import com.lhind.application.utility.model.flightdto.FlightResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(TripFlightController.BASE_URL)
@RequiredArgsConstructor
@Api(tags = {SwaggerConstant.TRIP_FLIGHT_API_TAG})
public class TripFlightController {

    public static final String BASE_URL = "/api/v1/users/trips/{tripId}/flights";

    private final TripFlightService tripFlightService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Find all flights booked by a trip", response = FlightResponseDto.class)
    public ResponseEntity<List<FlightResponseDto>> findAll(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {} to find all flights booked by trip: {}.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<FlightResponseDto> flights = tripFlightService.findAll(loggedUsername, tripId);

        log.info("Returning list of flights.");

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/departure")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Find all suggested departure flights booked by a trip", notes = "Trip status must be approved", response = FlightResponseDto.class)
    public ResponseEntity<List<FlightResponseDto>> findDepartureFlights(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/departure to find all suggested flights for trip: {}.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<FlightResponseDto> flights = tripFlightService.findDepartureFlights(loggedUsername, tripId);

        log.info("Returning list of flights.");

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/arrival")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Find all suggested arrival flights booked by a trip", notes = "Trip status must be approved", response = FlightResponseDto.class)
    public ResponseEntity<List<FlightResponseDto>> findArrivalFlights(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/arrival to find all suggested flights for trip: {}.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<FlightResponseDto> flights = tripFlightService.findArrivalFlights(loggedUsername, tripId);

        log.info("Returning list of flights.");

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{flightId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Find a flight booked by a trip by Id", notes = "Trip status must be approved", response = FlightResponseDto.class)
    public ResponseEntity<FlightResponseDto> findById(@PathVariable @Min(1) Long tripId,
                                                      @PathVariable @Min(1) Long flightId) {
        log.info("Accessing endpoint {}/{} to find flight on trip: {} by id.", BASE_URL, flightId, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        FlightResponseDto existingFlight = tripFlightService.findById(loggedUsername, tripId, flightId);

        log.info("Returning flight.");

        return new ResponseEntity<>(existingFlight, HttpStatus.OK);
    }

    @PatchMapping("/{flightId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Book a flight for a given trip", notes = "Trip status must be approved", response = FlightResponseDto.class)
    public ResponseEntity<FlightResponseDto> bookFlight(@PathVariable @Min(1) Long tripId,
                                                        @PathVariable @Min(1) Long flightId) {
        log.info("Accessing endpoint {}/{} to book a flight on trip: {} by id.", BASE_URL, flightId, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        FlightResponseDto addedFlight = tripFlightService.bookFlight(loggedUsername, tripId, flightId);

        log.info("Returning added flight.");

        return new ResponseEntity<>(addedFlight, HttpStatus.OK);
    }

}
