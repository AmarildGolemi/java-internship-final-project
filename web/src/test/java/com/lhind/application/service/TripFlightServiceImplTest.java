package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.entity.Trip;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.service.*;
import com.lhind.application.utility.model.flightdto.FlightFilterDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TripFlightServiceImplTest {

    private TripFlightService underTest;

    @Mock
    private UserTripService userTripService;
    @Mock
    private TripService tripService;
    @Mock
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        underTest = new TripFlightServiceImpl(userTripService, tripService, flightService);
    }

    @Test
    void canFindAllFlightsOfTrip() {
        //given
        String loggedUsername = "john";
        Long tripId = 1L;
        Trip foundTrip = new Trip();

        given(userTripService.findApprovedTrip(loggedUsername, tripId)).willReturn(foundTrip);

        //when
        underTest.findAll(loggedUsername, tripId);

        //then
        assertNotNull(foundTrip);
    }

    @Test
    void canFindFlightOfTripById() {
        //given
        String loggedUsername = "john";
        Long tripId = 1L;
        Long flightId = 1L;

        Flight flight = new Flight();
        flight.setId(flightId);

        Trip foundTrip = new Trip();
        foundTrip.setFlights(List.of(flight));

        given(userTripService.findApprovedTrip(loggedUsername, tripId)).willReturn(foundTrip);

        //when
        //then
        Assertions.assertDoesNotThrow(() -> underTest.findById(loggedUsername, tripId, flightId));
    }

    @Test
    void willThrowWhenFlightOfTripByIdNotFound() {
        //given
        String loggedUsername = "john";
        Long tripId = 1L;
        Long flightId = 1L;
        Trip foundTrip = new Trip();

        given(userTripService.findApprovedTrip(loggedUsername, tripId)).willReturn(foundTrip);

        //when
        //then
        assertThatThrownBy(() -> underTest.findById(loggedUsername, tripId, flightId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void canFindDepartureFlightsOfTrip() {
        //given
        String loggedUsername = "john";
        Long tripId = 1L;

        Trip foundTrip = new Trip();
        foundTrip.setFrom("tirana");
        foundTrip.setTo("rome");
        foundTrip.setDepartureDate(Date.valueOf("2021-12-25"));

        FlightFilterDto flightToFind = new FlightFilterDto();
        flightToFind.setFrom("tirana");
        flightToFind.setTo("rome");
        flightToFind.setDepartureDate(Date.valueOf("2021-12-25"));

        given(userTripService.findApprovedTrip(loggedUsername, tripId)).willReturn(foundTrip);

        //when
        underTest.findDepartureFlights(loggedUsername, tripId);

        //then
        verify(flightService).findFlights(flightToFind);
    }

    @Test
    void canFindArrivalFlightsOfTrip() {
        //given
        String loggedUsername = "john";
        Long tripId = 1L;

        Trip foundTrip = new Trip();
        foundTrip.setFrom("tirana");
        foundTrip.setTo("rome");
        foundTrip.setArrivalDate(Date.valueOf("2021-12-25"));

        FlightFilterDto flightToFind = new FlightFilterDto();
        flightToFind.setFrom("rome");
        flightToFind.setTo("tirana");
        flightToFind.setDepartureDate(Date.valueOf("2021-12-25"));

        given(userTripService.findApprovedTrip(loggedUsername, tripId)).willReturn(foundTrip);

        //when
        underTest.findArrivalFlights(loggedUsername, tripId);

        //then
        verify(flightService).findFlights(flightToFind);
    }

    @Test
    void canAddFlightToTrip() {
        //given
        String loggedUsername = "john";
        Long tripId = 1L;
        Long flightId = 1L;

        Trip tripToPatch = new Trip();
        Flight flightToAdd = new Flight();

        given(userTripService.findApprovedTrip(loggedUsername, tripId)).willReturn(tripToPatch);
        given(flightService.getById(flightId)).willReturn(flightToAdd);

        //when
        underTest.addFlight(loggedUsername, tripId, flightId);

        //then
        ArgumentCaptor<Trip> tripArgumentCaptor =
                ArgumentCaptor.forClass(Trip.class);

        verify(tripService).save(tripArgumentCaptor.capture());

        Trip capturedTrip = tripArgumentCaptor.getValue();

        assertThat(capturedTrip).isEqualTo(tripToPatch);
    }

    @Test
    void willThrowWhenFlightToAddToTripAlreadyAdded() {
        //given
        String loggedUsername = "john";
        Long tripId = 1L;
        Long flightId = 1L;

        Flight flightToAdd = new Flight();
        Trip tripToPatch = new Trip();
        tripToPatch.setFlights(List.of(flightToAdd));

        given(userTripService.findApprovedTrip(loggedUsername, tripId)).willReturn(tripToPatch);
        given(flightService.getById(flightId)).willReturn(flightToAdd);

        //when
        //then
        assertThatThrownBy(() -> underTest.addFlight(loggedUsername, tripId, flightId))
                .isInstanceOf(BadRequestException.class);
    }

}