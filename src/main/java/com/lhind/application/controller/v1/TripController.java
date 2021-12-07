package com.lhind.application.controller.v1;

import com.lhind.application.entity.Trip;
import com.lhind.application.service.TripService;
import com.lhind.application.utility.mapper.TripMapper;
import com.lhind.application.utility.model.tripdto.TripDto;
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
@RequestMapping(TripController.BASE_URL)
@RequiredArgsConstructor
public class TripController {

    public static final String BASE_URL = "/api/v1/trips";

    private final TripService tripService;

    @GetMapping
    public ResponseEntity<List<TripDto>> findAllWaitingForApproval() {
        List<TripDto> trips = tripService.findAllWaitingForApproval().stream()
                .map(TripMapper::tripToTripDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDto> findById(@PathVariable @Min(1) Long id) {
        Trip existingTrip = tripService.findById(id);

        return new ResponseEntity<>(TripMapper.tripToTripDto(existingTrip), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TripDto> approve(@PathVariable @Min(1) Long id) {
        Trip approvedTrip = tripService.approve(id);

        return new ResponseEntity<>(TripMapper.tripToTripDto(approvedTrip), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TripDto> reject(@PathVariable @Min(1) Long id) {
        Trip rejectedTrip = tripService.reject(id);

        return new ResponseEntity<>(TripMapper.tripToTripDto(rejectedTrip), HttpStatus.OK);
    }


}
