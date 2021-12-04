package com.lhind.tripapplication.service;

import com.lhind.tripapplication.entity.Flight;
import com.lhind.tripapplication.entity.Trip;
import com.lhind.tripapplication.utility.model.Status;
import com.lhind.tripapplication.utility.model.TripReason;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripService {

    List<Trip> findAll();

    Trip findById(Long id);

    List<Trip> findAllByTripReason(TripReason tripReason);

    List<Trip> findAllByStatus(Status status);

    List<Trip> findAllByTripReasonAndStatus(TripReason tripReason, Status status);

    @Transactional
    Trip save(Trip trip);

    @Transactional
    Trip update(Long id, Trip trip);

    @Transactional
    Trip approve(Long id);

    @Transactional
    Trip reject(Long id);

    @Transactional
    Trip patch(Long id, Trip trip);

    @Transactional
    String delete(Long id);

    @Transactional
    Trip sendForApproval(Long id);

    @Transactional
    Flight addFlight(Long id, Long flightId);
}
