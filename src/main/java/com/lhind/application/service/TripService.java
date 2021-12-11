package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.tripdto.TripDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripService {

    TripDto findById(Long id);

    List<TripDto> findAllWaitingForApproval();

    @Transactional
    Trip save(Trip trip);

    @Transactional
    Trip update(Trip trip);

    @Transactional
    Trip patch(Trip tripToPatch, Trip trip);

    @Transactional
    Trip sendForApproval(Trip tripToSend);

    @Transactional
    TripDto approve(Long tripId);

    @Transactional
    TripDto reject(Long tripId);

    @Transactional
    String delete(Trip tripToDelete);
}
