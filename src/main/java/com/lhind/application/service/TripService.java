package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
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
    Trip update(Trip trip);

    @Transactional
    Trip patch(Trip tripToPatch, Trip trip);

    @Transactional
    Trip sendForApproval(Trip tripToSend);

    @Transactional
    Trip approve(Trip tripToApprove);

    @Transactional
    Trip reject(Trip tripToReject);

    @Transactional
    String delete(Trip tripToDelete);
}
