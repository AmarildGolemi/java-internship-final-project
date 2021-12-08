package com.lhind.application.controller.v1;

import com.lhind.application.entity.Trip;
import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.service.TripService;
import com.lhind.application.utility.mapper.TripMapper;
import com.lhind.application.utility.model.tripdto.TripDto;
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
@RequestMapping(TripController.BASE_URL)
@RequiredArgsConstructor
public class TripController {

    public static final String BASE_URL = "/api/v1/trips";

    private final TripService tripService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TripDto>> findAllWaitingForApproval() {
        log.info("Accessing endpoint {} to find all trips waiting for approval.", BASE_URL);

        authenticatedUserService.getLoggedUsername();

        List<TripDto> trips = TripMapper.tripToTripDto(
                tripService.findAllWaitingForApproval()
        );

        log.info("Returning list of trips waiting for approval.");

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TripDto> findById(@PathVariable @Min(1) Long id) {
        log.info("Accessing endpoint {}/{} to find trips waiting for approval by id.", BASE_URL, id);

        authenticatedUserService.getLoggedUsername();

        Trip existingTrip = tripService.findById(id);

        log.info("Returning trip.");

        return new ResponseEntity<>(TripMapper.tripToTripDto(existingTrip), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TripDto> approve(@PathVariable @Min(1) Long id) {
        log.info("Accessing endpoint {}/{} to approve trip by id.", BASE_URL, id);

        authenticatedUserService.getLoggedUsername();

        Trip approvedTrip = tripService.approve(id);

        log.info("Returning approved trip.");

        return new ResponseEntity<>(TripMapper.tripToTripDto(approvedTrip), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TripDto> reject(@PathVariable @Min(1) Long id) {
        log.info("Accessing endpoint {}/{} to reject trip by id.", BASE_URL, id);

        authenticatedUserService.getLoggedUsername();

        Trip rejectedTrip = tripService.reject(id);

        log.info("Returning rejected trip.");

        return new ResponseEntity<>(TripMapper.tripToTripDto(rejectedTrip), HttpStatus.OK);
    }

}
