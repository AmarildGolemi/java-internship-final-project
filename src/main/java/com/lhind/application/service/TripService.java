package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.tripdto.TripResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripService {

    TripResponseDto findById(Long id);

    List<TripResponseDto> findAllWaitingForApproval();

    @Transactional
    Trip save(Trip trip);

    @Transactional
    Trip update(Trip trip);

    @Transactional
    Trip patch(Trip tripToPatch, Trip trip);

    @Transactional
    Trip sendForApproval(Trip tripToSend);

    @Transactional
    TripResponseDto approve(Long tripId);

    @Transactional
    TripResponseDto reject(Long tripId);

    @Transactional
    String delete(Trip tripToDelete);
}
