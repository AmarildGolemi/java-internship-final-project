package com.lhind.application.controller.v1;

import com.lhind.application.service.AuthenticatedUserService;
import com.lhind.application.service.FlightService;
import com.lhind.application.utility.model.flightdto.*;
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
@RequestMapping(FlightController.BASE_URL)
@RequiredArgsConstructor
public class FlightController {

    public static final String BASE_URL = "/api/v1/flights";

    private final FlightService flightService;
    private final AuthenticatedUserService authenticatedUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<FlightResponseDto>> findAll() {
        log.info("Accessing endpoint {} to find all available flights.", BASE_URL);

        authenticatedUserService.getLoggedUsername();

        List<FlightResponseDto> flights = flightService.findAll();

        log.info("Returning list of all available flights.");

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FlightResponseDto> findById(@PathVariable @Min(1) Long id) {
        log.info("Accessing endpoint {}/{} to find flight by id.", BASE_URL, id);

        authenticatedUserService.getLoggedUsername();

        FlightResponseDto existingFlight = flightService.findById(id);

        log.info("Returning flight.");

        return new ResponseEntity<>(existingFlight, HttpStatus.OK);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    public ResponseEntity<List<FlightResponseDto>> findFlights(@Valid @RequestBody FlightFilterDto flightDto) {
        log.info("Accessing endpoint {}/find to find flight by filters: {}.", BASE_URL, flightDto);

        authenticatedUserService.getLoggedUsername();

        List<FlightResponseDto> flights = flightService.findFlights(flightDto);

        log.info("Returning list of filtered flights.");

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FlightResponseDto> save(@Valid @RequestBody FlightRequestDto flightDto) {
        log.info("Accessing endpoint {} to post a new flight: {}.", BASE_URL, flightDto);

        authenticatedUserService.getLoggedUsername();

        FlightResponseDto savedFlight = flightService.save(flightDto);

        log.info("Returning new added flight.");

        return new ResponseEntity<>(savedFlight, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FlightResponseDto> update(@PathVariable Long id,
                                                    @Valid @RequestBody FlightRequestDto flightDto) {
        log.info("Accessing endpoint {}/{} to update flight by id.", BASE_URL, id);

        authenticatedUserService.getLoggedUsername();

        FlightResponseDto updatedFlight = flightService.update(id, flightDto);

        log.info("Returning updated flight.");

        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FlightResponseDto> patch(@PathVariable Long id,
                                                   @Valid @RequestBody FlightPatchDto flightDto) {
        log.info("Accessing endpoint {}/{} to patch flight by id.", BASE_URL, id);

        authenticatedUserService.getLoggedUsername();

        FlightResponseDto patchedFlight = flightService.patch(id, flightDto);

        log.info("Returning patched flight.");

        return new ResponseEntity<>(patchedFlight, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Accessing endpoint {}/{} to delete flight by id.", BASE_URL, id);

        authenticatedUserService.getLoggedUsername();

        log.info("Returning confirmation message.");

        return new ResponseEntity<>(flightService.delete(id), HttpStatus.OK);
    }

}
