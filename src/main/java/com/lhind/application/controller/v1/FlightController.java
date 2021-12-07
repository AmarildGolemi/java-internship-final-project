package com.lhind.application.controller.v1;

import com.lhind.application.entity.Flight;
import com.lhind.application.service.FlightService;
import com.lhind.application.utility.mapper.FlightMapper;
import com.lhind.application.utility.model.FlightDto.FindFlightsDto;
import com.lhind.application.utility.model.FlightDto.FlightDto;
import com.lhind.application.utility.model.FlightDto.FlightPatchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(FlightController.BASE_URL)
@RequiredArgsConstructor
public class FlightController {

    public static final String BASE_URL = "api/v1/flights";

    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightDto>> findAll() {
        List<FlightDto> flights = flightService.findAll()
                .stream()
                .map(FlightMapper::flightToFlightDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> findById(@PathVariable @Min(1) Long id) {
        Flight existingFlight = flightService.findById(id);

        return new ResponseEntity<>(FlightMapper.flightToFlightDto(existingFlight), HttpStatus.OK);
    }

    @PostMapping("/find")
    public ResponseEntity<List<FlightDto>> findFlights(@Valid @RequestBody FindFlightsDto flightDto) {
        List<FlightDto> flights = flightService.findFlights(FlightMapper.flightDtoToFlight(flightDto)).stream()
                .map(FlightMapper::flightToFlightDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FlightDto> save(@Valid @RequestBody FlightDto flightDto) {
        Flight flightToSave = FlightMapper.flightDtoToFlight(flightDto);
        Flight savedFlight = flightService.save(flightToSave);

        return new ResponseEntity<>(FlightMapper.flightToFlightDto(savedFlight), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDto> update(@PathVariable Long id,
                                            @Valid @RequestBody FlightDto flightDto) {
        Flight flightToUpdate = FlightMapper.flightDtoToFlight(flightDto);
        Flight updatedFlight = flightService.update(id, flightToUpdate);

        return new ResponseEntity<>(FlightMapper.flightToFlightDto(updatedFlight), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FlightDto> patch(@PathVariable Long id,
                                           @Valid @RequestBody FlightPatchDto flightDto) {
        Flight flightToPatch = FlightMapper.flightDtoToFlight(flightDto);
        Flight patchedFlight = flightService.patch(id, flightToPatch);

        return new ResponseEntity<>(FlightMapper.flightToFlightDto(patchedFlight), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.delete(id), HttpStatus.OK);
    }

}
