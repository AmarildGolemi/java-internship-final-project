package com.lhind.application.repository;

import com.lhind.application.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    String queryDirectFlight = "SELECT * FROM flight " +
            "WHERE from_city = ?1 " +
            "AND to_city = ?2 " +
            "AND departure_date = ?3 " +
            "UNION ";
    String queryFirstConnectedFlight = "SELECT a.* FROM flight a join flight b " +
            "on a.to_city = b.from_city " +
            "where a.from_city like ?1 " +
            "and b.to_city like ?2 " +
            "and a.departure_date LIKE ?3 UNION ";
    String querySecondConnectedFlight = "SELECT b.* FROM flight a join flight b " +
            "on a.to_city = b.from_city " +
            "where a.from_city like ?1 " +
            "and b.to_city like ?2 " +
            "and a.departure_date LIKE ?3 ";

    @Query(
            value = queryDirectFlight + queryFirstConnectedFlight + querySecondConnectedFlight,
            nativeQuery = true
    )
    List<Flight> findFlights(String from, String to, String departureDate);

}
