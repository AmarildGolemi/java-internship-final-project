package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.tripdto.TripFilterDto;
import com.lhind.application.utility.model.tripdto.TripPatchDto;
import com.lhind.application.utility.model.tripdto.TripRequestDto;
import com.lhind.application.utility.model.tripdto.TripResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserTripService {

    List<TripResponseDto> findAll(String loggedUsername);

    TripResponseDto findById(String loggedUsername, Long tripId);

    Trip findApprovedTrip(String loggedUsername, Long tripId);

    List<TripResponseDto> findAllFilteredTrips(String loggedUsername, TripFilterDto tripDto);

    @Transactional
    TripResponseDto addTrip(String loggedUsername, TripRequestDto tripToAdd);

    @Transactional
    TripResponseDto update(String loggedUsername, Long tripId, TripRequestDto trip);

    @Transactional
    TripResponseDto patch(String loggedUsername, Long tripId, TripPatchDto trip);

    @Transactional
    TripResponseDto sendForApproval(String loggedUsername, Long tripId);

    @Transactional
    String delete(String loggedUsername, Long tripId);
}
