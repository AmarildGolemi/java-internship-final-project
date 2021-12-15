package com.lhind.application.controller.v1;

import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.service.TripService;
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

import javax.validation.constraints.Min;
import java.util.List;

import static com.lhind.application.controller.v1.TripController.BASE_URL;
import static com.lhind.application.swagger.SwaggerConstant.TRIP_API_TAG;

@Slf4j
@Validated
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Api(tags = {TRIP_API_TAG})
public class TripController {

    public static final String BASE_URL = "/api/v1/trips";

    private final TripService tripService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Find all trips waiting for approval", response = TripResponseDto.class)
    public ResponseEntity<List<TripResponseDto>> findAllWaitingForApproval() {
        log.info("Accessing endpoint {} to find all trips waiting for approval.", BASE_URL);

        authenticatedUserService.getLoggedUsername();

        List<TripResponseDto> trips = tripService.findAllWaitingForApproval();

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Find existing trip waiting for approval by id", response = TripResponseDto.class)
    public ResponseEntity<TripResponseDto> findById(@PathVariable @Min(1) Long id) {
        log.info("Accessing endpoint {}/{} to find trips waiting for approval by id.", BASE_URL, id);

        authenticatedUserService.getLoggedUsername();

        TripResponseDto foundTrip = tripService.findById(id);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(foundTrip, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Approve a trip", response = TripResponseDto.class)
    public ResponseEntity<TripResponseDto> approve(@PathVariable @Min(1) Long id) {
        log.info("Accessing endpoint {}/{} to approve trip by id.", BASE_URL, id);

        authenticatedUserService.getLoggedUsername();

        TripResponseDto approvedTrip = tripService.approve(id);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(approvedTrip, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Reject a trip", response = TripResponseDto.class)
    public ResponseEntity<TripResponseDto> reject(@PathVariable @Min(1) Long id) {
        log.info("Accessing endpoint {}/{} to reject trip by id.", BASE_URL, id);

        authenticatedUserService.getLoggedUsername();

        TripResponseDto rejectedTrip = tripService.reject(id);

        log.info("Returning Response Entity.");

        return new ResponseEntity<>(rejectedTrip, HttpStatus.OK);
    }

}
