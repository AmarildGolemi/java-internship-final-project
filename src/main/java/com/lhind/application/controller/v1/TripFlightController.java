package com.lhind.application.controller.v1;

import com.lhind.application.entity.Flight;
import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.service.TripFlightService;
import com.lhind.application.utility.mapper.FlightMapper;
import com.lhind.application.utility.model.flightdto.FlightDto;
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
public class TripFlightController {

    public static final String BASE_URL = "/api/v1/users/trips/{tripId}/flights";

    private final TripFlightService tripFlightService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<FlightDto>> findAll(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {} to find all flights of trip: {}.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<FlightDto> flights = FlightMapper.flightToFlightDto(
                tripFlightService.findAll(loggedUsername, tripId)
        );

        log.info("Returning list of flights.");

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/suggested")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<FlightDto>> findFlights(@PathVariable @Min(1) Long tripId) {
        log.info("Accessing endpoint {}/suggested to find all suggested flights for trip: {}.", BASE_URL, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        List<FlightDto> flights = FlightMapper.flightToFlightDto(
                tripFlightService.findFlights(loggedUsername, tripId)
        );

        log.info("Returning list of flights.");

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{flightId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<FlightDto> findById(@PathVariable @Min(1) Long tripId,
                                              @PathVariable @Min(1) Long flightId) {
        log.info("Accessing endpoint {}/{} to find flight on trip: {} by id.", BASE_URL, flightId, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        Flight existingFlight = tripFlightService.findById(loggedUsername, tripId, flightId);

        log.info("Returning flight.");

        return new ResponseEntity<>(FlightMapper.flightToFlightDto(existingFlight), HttpStatus.OK);
    }

    @PatchMapping("/{flightId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<FlightDto> addFlight(@PathVariable @Min(1) Long tripId,
                                               @PathVariable @Min(1) Long flightId) {
        log.info("Accessing endpoint {}/{} to add flight on trip: {} by id.", BASE_URL, flightId, tripId);

        String loggedUsername = authenticatedUserService.getLoggedUsername();

        Flight addedFlight = tripFlightService.addFlight(loggedUsername, tripId, flightId);

        log.info("Returning added flight.");

        return new ResponseEntity<>(FlightMapper.flightToFlightDto(addedFlight), HttpStatus.OK);
    }

}
