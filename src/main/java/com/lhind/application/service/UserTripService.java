package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.tripdto.TripFilterDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserTripService {

    List<Trip> findAll(String loggedUsername);

    Trip findById(String loggedUsername, Long tripId);

    List<Trip> findAllFilteredTrips(String loggedUsername, TripFilterDto tripDto);

    @Transactional
    Trip addTrip(String loggedUsername, Trip tripToAdd);

    @Transactional
    Trip update(String loggedUsername, Long tripId, Trip trip);

    @Transactional
    Trip patch(String loggedUsername, Long tripId, Trip trip);

    @Transactional
    Trip sendForApproval(String loggedUsername, Long tripId);

    @Transactional
    String delete(String loggedUsername, Long tripId);
}
