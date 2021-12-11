package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.tripdto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserTripService {

    List<TripDto> findAll(String loggedUsername);

    TripDto findById(String loggedUsername, Long tripId);

    Trip findApprovedTrip(String loggedUsername, Long tripId);

    List<TripDto> findAllFilteredTrips(String loggedUsername, TripFilterDto tripDto);

    @Transactional
    TripDto addTrip(String loggedUsername, TripPostDto tripToAdd);

    @Transactional
    TripDto update(String loggedUsername, Long tripId, TripUpdateDto trip);

    @Transactional
    TripDto patch(String loggedUsername, Long tripId, TripPatchDto trip);

    @Transactional
    TripDto sendForApproval(String loggedUsername, Long tripId);

    @Transactional
    String delete(String loggedUsername, Long tripId);
}
