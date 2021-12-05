package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserTripService {

    List<Trip> findAll(Long userId);

    Trip findById(Long userId, Long id);

    List<Trip> findAllByTripReason(Long userId, TripReason tripReason);

    List<Trip> findAllByStatus(Long userId, Status status);

    List<Trip> findAllByTripReasonAndStatus(Long userId, TripReason tripReason, Status status);

    @Transactional
    Trip addTrip(Long userId, Trip trip);

    @Transactional
    Trip update(Long userId, Long tripId, Trip trip);

    @Transactional
    Trip sendForApproval(Long userId, Long tripId);

    @Transactional
    Trip patch(Long userId, Long tripId, Trip trip);

    @Transactional
    String delete(Long userId, Long tripId);
}
