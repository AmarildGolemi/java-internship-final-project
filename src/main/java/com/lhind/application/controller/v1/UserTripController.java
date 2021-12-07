package com.lhind.application.controller.v1;

import com.lhind.application.entity.Trip;
import com.lhind.application.service.UserTripService;
import com.lhind.application.utility.mapper.TripMapper;
import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.tripdto.TripDto;
import com.lhind.application.utility.model.tripdto.TripPatchDto;
import com.lhind.application.utility.model.TripReason;
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
@RequestMapping(UserTripController.BASE_URL)
@RequiredArgsConstructor
public class UserTripController {

    public static final String BASE_URL = "api/v1/users/{userId}/trips";

    private final UserTripService userTripService;

    @GetMapping
    public ResponseEntity<List<TripDto>> findAll(@PathVariable @Min(1) Long userId) {
        List<TripDto> trips = userTripService.findAll(userId)
                .stream()
                .map(TripMapper::tripToTripDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/reason/{tripReason}")
    public ResponseEntity<List<TripDto>> findAllByTripReason(@PathVariable @Min(1) Long userId,
                                                             @PathVariable TripReason tripReason) {
        List<TripDto> trips = userTripService.findAllByTripReason(userId, tripReason)
                .stream()
                .map(TripMapper::tripToTripDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TripDto>> findAllByStatus(@PathVariable @Min(1) Long userId,
                                                         @PathVariable Status status) {
        List<TripDto> trips = userTripService.findAllByStatus(userId, status)
                .stream()
                .map(TripMapper::tripToTripDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{tripReason}/{status}")
    public ResponseEntity<List<TripDto>> findAllByTripReasonAndStatus(@PathVariable @Min(1) Long userId,
                                                                      @PathVariable TripReason tripReason,
                                                                      @PathVariable Status status) {
        List<TripDto> trips = userTripService.findAllByTripReasonAndStatus(userId, tripReason, status)
                .stream()
                .map(TripMapper::tripToTripDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripDto> findById(@PathVariable @Min(1) Long userId,
                                            @PathVariable @Min(1) Long tripId) {
        Trip existingTrip = userTripService.findById(userId, tripId);

        return new ResponseEntity<>(TripMapper.tripToTripDto(existingTrip), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TripDto> addTrip(@PathVariable @Min(1) Long userId,
                                           @Valid @RequestBody TripDto tripDto) {
        Trip tripToAdd = TripMapper.tripDtoToTrip(tripDto);
        Trip addedTrip = userTripService.addTrip(userId, tripToAdd);

        return new ResponseEntity<>(TripMapper.tripToTripDto(addedTrip), HttpStatus.CREATED);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripDto> update(@PathVariable @Min(1) Long userId,
                                          @PathVariable @Min(1) Long tripId,
                                          @Valid @RequestBody TripDto tripDto) {
        Trip tripToUpdate = TripMapper.tripDtoToTrip(tripDto);
        Trip addedTrip = userTripService.update(userId, tripId, tripToUpdate);

        return new ResponseEntity<>(TripMapper.tripToTripDto(addedTrip), HttpStatus.OK);
    }

    @PatchMapping("/{tripId}")
    public ResponseEntity<TripDto> patch(@PathVariable @Min(1) Long userId,
                                         @PathVariable @Min(1) Long tripId,
                                         @Valid @RequestBody TripPatchDto tripDto) {
        Trip tripToPatch = TripMapper.tripDtoToTrip(tripDto);
        Trip patchedTrip = userTripService.patch(userId, tripId, tripToPatch);

        return new ResponseEntity<>(TripMapper.tripToTripDto(patchedTrip), HttpStatus.OK);
    }

    @PostMapping("/{tripId}")
    public ResponseEntity<TripDto> sendForApproval(@PathVariable @Min(1) Long userId,
                                                   @PathVariable @Min(1) Long tripId) {
        Trip sentTrip = userTripService.sendForApproval(userId, tripId);

        return new ResponseEntity<>(TripMapper.tripToTripDto(sentTrip), HttpStatus.OK);
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<String> delete(@PathVariable @Min(1) Long userId,
                                         @PathVariable @Min(1) Long tripId) {
        return new ResponseEntity<>(userTripService.delete(userId, tripId), HttpStatus.OK);
    }

}
