package com.lhind.application.repository;

import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.Status;
import com.lhind.application.utility.model.TripReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByTripReason(TripReason tripReason);

    List<Trip> findAllByStatus(Status status);

    List<Trip> findAllByTripReasonAndStatus(TripReason tripReason, Status status);
}
