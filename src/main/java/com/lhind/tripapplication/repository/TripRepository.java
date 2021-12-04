package com.lhind.tripapplication.repository;

import com.lhind.tripapplication.entity.Trip;
import com.lhind.tripapplication.utility.model.Status;
import com.lhind.tripapplication.utility.model.TripReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByTripReason(TripReason tripReason);

    List<Trip> findAllByStatus(Status status);

    List<Trip> findAllByTripReasonAndStatus(TripReason tripReason, Status status);
}
