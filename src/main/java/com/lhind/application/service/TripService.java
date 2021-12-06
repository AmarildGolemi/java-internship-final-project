package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripService {

    Trip findById(Long id);

    List<Trip> findAllWaitingForApproval();

    @Transactional
    Trip save(Trip trip);

    @Transactional
    Trip update(Trip trip);

    @Transactional
    Trip patch(Trip tripToPatch, Trip trip);

    @Transactional
    Trip sendForApproval(Trip tripToSend);

    @Transactional
    Trip approve(Long tripId);

    @Transactional
    Trip reject(Long tripId);

    @Transactional
    String delete(Trip tripToDelete);
}
