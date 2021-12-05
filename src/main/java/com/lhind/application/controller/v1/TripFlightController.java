package com.lhind.application.controller.v1;

import com.lhind.application.entity.Flight;
import com.lhind.application.service.TripFlightService;
import com.lhind.application.utility.mapper.FlightMapper;
import com.lhind.application.utility.model.FlightDto.FlightDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(TripFlightController.BASE_URL)
@RequiredArgsConstructor
public class TripFlightController {

    private final TripFlightService tripFlightService;

    public static final String BASE_URL = "/api/v1/users/{userId}/trips/{tripId}/flights";

    @GetMapping
    public ResponseEntity<List<FlightDto>> findAll(@PathVariable @Min(1) Long userId,
                                                   @PathVariable @Min(1) Long tripId) {
        List<FlightDto> flights = tripFlightService.findAll(userId, tripId)
                .stream()
                .map(FlightMapper::flightToFlightDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<FlightDto>> findAvailableFlights(@PathVariable @Min(1) Long userId,
                                                                @PathVariable @Min(1) Long tripId) {
        List<FlightDto> flights = tripFlightService.findAvailableFlights(userId, tripId).stream()
                .map(FlightMapper::flightToFlightDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDto> findById(@PathVariable @Min(1) Long userId,
                                              @PathVariable @Min(1) Long tripId,
                                              @PathVariable @Min(1) Long flightId) {
        Flight existingFlight = tripFlightService.findById(userId, tripId, flightId);

        return new ResponseEntity<>(FlightMapper.flightToFlightDto(existingFlight), HttpStatus.OK);
    }


    @PatchMapping("/{flightId}")
    public ResponseEntity<FlightDto> addFlight(@PathVariable @Min(1) Long userId,
                                               @PathVariable @Min(1) Long tripId,
                                               @PathVariable @Min(1) Long flightId) {
        Flight addedFlight = tripFlightService.addFlight(userId, tripId, flightId);

        return new ResponseEntity<>(FlightMapper.flightToFlightDto(addedFlight), HttpStatus.OK);
    }

}
