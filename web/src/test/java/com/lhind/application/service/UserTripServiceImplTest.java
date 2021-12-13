package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.entity.User;
import com.lhind.application.exception.BadRequestException;
import com.lhind.application.exception.InvalidDateTimeException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.service.TripService;
import com.lhind.application.service.UserService;
import com.lhind.application.service.UserTripService;
import com.lhind.application.service.UserTripServiceImpl;
import com.lhind.application.utility.mapper.TripMapper;
import com.lhind.application.utility.model.tripdto.TripFilterDto;
import com.lhind.application.utility.model.tripdto.TripPatchDto;
import com.lhind.application.utility.model.tripdto.TripRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ValidationException;
import java.sql.Date;
import java.util.List;

import static com.lhind.application.utility.model.Status.APPROVED;
import static com.lhind.application.utility.model.Status.CREATED;
import static com.lhind.application.utility.model.TripReason.EVENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserTripServiceImplTest {

    private UserTripService underTest;

    @Mock
    private UserService userService;

    @Mock
    private TripService tripService;

    @BeforeEach
    void setUp() {
        underTest = new UserTripServiceImpl(userService, tripService);
    }

    @Test
    void canFindAllTrips() {
        //given
        String loggedUsername = "john";
        User foundUser = new User();

        given(userService.getByUsername(loggedUsername)).willReturn(foundUser);

        //when
        underTest.findAll(loggedUsername);

        //then
        assertNotNull(foundUser);
    }

    @Test
    void canFindTripById() {
        //given
        Long tripId = 1L;
        String loggedUsername = "john";
        User foundUser = new User();

        Trip trip = new Trip();
        trip.setId(tripId);

        foundUser.setTrips(List.of(trip));

        given(userService.getByUsername(loggedUsername)).willReturn(foundUser);

        //when
        //then
        Assertions.assertDoesNotThrow(() -> underTest.findById(loggedUsername, tripId));
    }

    @Test
    void willThrowWhenTripsNotFoundById() {
        //given
        Long tripId = 1L;
        String loggedUsername = "john";
        User foundUser = new User();

        given(userService.getByUsername(loggedUsername)).willReturn(foundUser);

        //when
        //then
        assertThatThrownBy(() -> underTest.findById(loggedUsername, tripId))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void canFindApprovedTrip() {
        //given
        Long tripId = 1L;
        String loggedUsername = "john";
        User foundUser = new User();
        Trip trip = new Trip();
        trip.setId(tripId);
        trip.setStatus(APPROVED);

        foundUser.setTrips(List.of(trip));

        given(userService.getByUsername(loggedUsername)).willReturn(foundUser);

        //when
        underTest.findApprovedTrip(loggedUsername, tripId);

        //then
        Assertions.assertDoesNotThrow(() -> underTest.findApprovedTrip(loggedUsername, tripId));
    }

    @Test
    void willThrowWhenFindingApprovedTrip() {
        //given
        Long tripId = 1L;
        String loggedUsername = "john";
        User foundUser = new User();

        given(userService.getByUsername(loggedUsername)).willReturn(foundUser);

        //when
        assertThatThrownBy(() -> underTest.findApprovedTrip(loggedUsername, tripId))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void willThrowWhenFindingNoTripFilter() {
        //given
        TripFilterDto tripFilterDto = new TripFilterDto();

        String loggedUsername = "john";
        User foundUser = new User();

        given(userService.getByUsername(loggedUsername)).willReturn(foundUser);

        //when
        //then
        assertThatThrownBy(() -> underTest.findAllFilteredTrips(loggedUsername, tripFilterDto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void canFindAllTripFilterByStatus() {
        //given
        TripFilterDto tripFilterDto = new TripFilterDto();
        tripFilterDto.setStatus(CREATED);

        String loggedUsername = "john";
        User foundUser = new User();

        given(userService.getByUsername(loggedUsername)).willReturn(foundUser);

        //when
        //then
        Assertions.assertDoesNotThrow(() -> underTest.findAllFilteredTrips(loggedUsername, tripFilterDto));
    }

    @Test
    void canFindAllTripFilterByReason() {
        //given
        TripFilterDto tripFilterDto = new TripFilterDto();
        tripFilterDto.setTripReason(EVENT);

        String loggedUsername = "john";
        User foundUser = new User();

        given(userService.getByUsername(loggedUsername)).willReturn(foundUser);

        //when
        //then
        Assertions.assertDoesNotThrow(() -> underTest.findAllFilteredTrips(loggedUsername, tripFilterDto));
    }

    @Test
    void canFindAllTripFilterByReasonAndStatus() {
        //given
        TripFilterDto tripFilterDto = new TripFilterDto();
        tripFilterDto.setTripReason(EVENT);
        tripFilterDto.setStatus(CREATED);

        String loggedUsername = "john";
        User foundUser = new User();

        given(userService.getByUsername(loggedUsername)).willReturn(foundUser);

        //when
        //then
        Assertions.assertDoesNotThrow(() -> underTest.findAllFilteredTrips(loggedUsername, tripFilterDto));
    }

    @Test
    void willThrowWhenAddingTrip() {
        //given
        TripRequestDto tripDto = new TripRequestDto();
        tripDto.setTripReason(EVENT);
        tripDto.setDescription("test");
        tripDto.setFrom("tirana");
        tripDto.setTo("rome");
        tripDto.setDepartureDate(Date.valueOf("2021-12-25"));
        tripDto.setArrivalDate(Date.valueOf("2021-12-25"));

        Trip tripToAdd = TripMapper.tripDtoToTrip(tripDto);

        String loggedUsername = "john";
        User foundUser = new User();
        foundUser.setTrips(List.of(tripToAdd));

        given(userService.getByUsername(loggedUsername)).willReturn(foundUser);

        //when
        //then
        assertThatThrownBy(() -> underTest.addTrip(loggedUsername, tripDto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void canAddTrip() {
        //given
        TripRequestDto tripDto = new TripRequestDto();
        tripDto.setTripReason(EVENT);
        tripDto.setDescription("test");
        tripDto.setFrom("tirana");
        tripDto.setTo("rome");
        tripDto.setDepartureDate(Date.valueOf("2021-12-25"));
        tripDto.setArrivalDate(Date.valueOf("2021-12-25"));

        Trip tripToAdd = TripMapper.tripDtoToTrip(tripDto);

        String loggedUsername = "john";
        User userToPatch = new User();

        given(userService.getByUsername(loggedUsername)).willReturn(userToPatch);

        //when
        underTest.addTrip(loggedUsername, tripDto);

        //then
        ArgumentCaptor<Trip> tripArgumentCaptor =
                ArgumentCaptor.forClass(Trip.class);

        verify(tripService).save(tripArgumentCaptor.capture());

        Trip capturedTrip = tripArgumentCaptor.getValue();

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userService).saveUserAfterAddingNewTrip(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedTrip).isEqualTo(tripToAdd);
        assertThat(capturedUser).isEqualTo(userToPatch);
    }

    @Test
    void canUpdateTrip() {
        //given
        Long tripId = 1L;

        TripRequestDto tripDto = new TripRequestDto();
        tripDto.setTripReason(EVENT);
        tripDto.setDescription("test");
        tripDto.setFrom("tirana");
        tripDto.setTo("rome");
        tripDto.setDepartureDate(Date.valueOf("2021-12-25"));
        tripDto.setArrivalDate(Date.valueOf("2021-12-25"));

        Trip tripToUpdate = TripMapper.tripDtoToTrip(tripDto);
        tripToUpdate.setId(tripId);
        tripToUpdate.setStatus(CREATED);

        String loggedUsername = "john";
        User userToPatch = new User();
        userToPatch.setTrips(List.of(tripToUpdate));

        given(userService.getByUsername(loggedUsername)).willReturn(userToPatch);

        //when
        underTest.update(loggedUsername, tripId, tripDto);

        //then
        ArgumentCaptor<Trip> tripArgumentCaptor =
                ArgumentCaptor.forClass(Trip.class);

        verify(tripService).update(tripArgumentCaptor.capture());

        Trip capturedTrip = tripArgumentCaptor.getValue();


        assertThat(capturedTrip).isEqualTo(tripToUpdate);
    }

    @Test
    void willThrowWhenNotFindingTripToUpdate() {
        //given
        Long tripId = 1L;

        TripRequestDto tripDto = new TripRequestDto();
        tripDto.setTripReason(EVENT);
        tripDto.setDescription("test");
        tripDto.setFrom("tirana");
        tripDto.setTo("rome");
        tripDto.setDepartureDate(Date.valueOf("2021-12-25"));
        tripDto.setArrivalDate(Date.valueOf("2021-12-25"));

        Trip tripToUpdate = TripMapper.tripDtoToTrip(tripDto);
        tripToUpdate.setId(tripId);
        tripToUpdate.setStatus(CREATED);

        String loggedUsername = "john";
        User userToPatch = new User();

        given(userService.getByUsername(loggedUsername)).willReturn(userToPatch);

        //when
        //then
        assertThatThrownBy(() -> underTest.update(loggedUsername, tripId, tripDto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void willThrowWhenTripToUpdateDatesNotProvided() {
        //given
        Long tripId = 1L;

        TripRequestDto tripDto = new TripRequestDto();
        tripDto.setTripReason(EVENT);
        tripDto.setDescription("test");
        tripDto.setFrom("tirana");
        tripDto.setTo("rome");
        tripDto.setDepartureDate(Date.valueOf("2021-12-30"));
        tripDto.setArrivalDate(Date.valueOf("2021-12-25"));

        String loggedUsername = "john";

        //when
        //then
        assertThatThrownBy(() -> underTest.update(loggedUsername, tripId, tripDto))
                .isInstanceOf(InvalidDateTimeException.class);
    }

    @Test
    void willThrowWhenTripToUpdateDatesNotValid() {
        //given
        Long tripId = 1L;

        TripRequestDto tripDto = new TripRequestDto();
        tripDto.setTripReason(EVENT);
        tripDto.setDescription("test");
        tripDto.setFrom("tirana");
        tripDto.setTo("rome");

        String loggedUsername = "john";

        //when
        //then
        assertThatThrownBy(() -> underTest.update(loggedUsername, tripId, tripDto))
                .isInstanceOf(InvalidDateTimeException.class);
    }

    @Test
    void canPatchTrip() {
        //given
        Long tripId = 1L;

        TripPatchDto tripDto = new TripPatchDto();
        tripDto.setTripReason(EVENT);
        tripDto.setDescription("test");
        tripDto.setFrom("tirana");
        tripDto.setTo("rome");
        tripDto.setDepartureDate(Date.valueOf("2021-12-25"));
        tripDto.setArrivalDate(Date.valueOf("2021-12-25"));

        Trip tripToPatch = TripMapper.tripDtoToTrip(tripDto);

        Trip foundTrip = TripMapper.tripDtoToTrip(tripDto);
        foundTrip.setId(tripId);
        foundTrip.setStatus(CREATED);

        String loggedUsername = "john";
        User userToPatch = new User();
        userToPatch.setTrips(List.of(foundTrip));

        given(userService.getByUsername(loggedUsername)).willReturn(userToPatch);

        //when
        underTest.patch(loggedUsername, tripId, tripDto);

        //then
        verify(tripService).patch(foundTrip, tripToPatch);
    }

    @Test
    void willThrowWhenTripPatchDatesNotValid() {
        //given
        Long tripId = 1L;

        TripPatchDto tripDto = new TripPatchDto();
        tripDto.setTripReason(EVENT);
        tripDto.setDescription("test");
        tripDto.setFrom("tirana");
        tripDto.setTo("rome");
        tripDto.setDepartureDate(Date.valueOf("2021-12-30"));
        tripDto.setArrivalDate(Date.valueOf("2021-12-25"));

        String loggedUsername = "john";

        //when
        //then
        assertThatThrownBy(() -> underTest.patch(loggedUsername, tripId, tripDto))
                .isInstanceOf(InvalidDateTimeException.class);
    }

    @Test
    void willThrowWhenTripPatchDatesOnlyOneProvided() {
        //given
        Long tripId = 1L;

        TripPatchDto tripDto = new TripPatchDto();
        tripDto.setTripReason(EVENT);
        tripDto.setDescription("test");
        tripDto.setFrom("tirana");
        tripDto.setTo("rome");
        tripDto.setArrivalDate(Date.valueOf("2021-12-25"));

        String loggedUsername = "john";

        //when
        //then
        assertThatThrownBy(() -> underTest.patch(loggedUsername, tripId, tripDto))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void canSendForApproval() {
        //given
        Long tripId = 1L;

        Trip tripToSend = new Trip();
        tripToSend.setId(tripId);
        tripToSend.setTripReason(EVENT);
        tripToSend.setDescription("test");
        tripToSend.setFrom("tirana");
        tripToSend.setTo("rome");
        tripToSend.setDepartureDate(Date.valueOf("2021-12-25"));
        tripToSend.setArrivalDate(Date.valueOf("2021-12-25"));
        tripToSend.setStatus(CREATED);

        String loggedUsername = "john";
        User userToPatch = new User();
        userToPatch.setTrips(List.of(tripToSend));

        given(userService.getByUsername(loggedUsername)).willReturn(userToPatch);

        //when
        underTest.sendForApproval(loggedUsername, tripId);

        //then
        ArgumentCaptor<Trip> tripArgumentCaptor =
                ArgumentCaptor.forClass(Trip.class);

        verify(tripService).sendForApproval(tripArgumentCaptor.capture());

        Trip capturedTrip = tripArgumentCaptor.getValue();

        assertThat(capturedTrip).isEqualTo(tripToSend);
    }

    @Test
    void canDeleteTrip() {
        //given
        Long tripId = 1L;

        Trip delete = new Trip();
        delete.setId(tripId);
        delete.setTripReason(EVENT);
        delete.setDescription("test");
        delete.setFrom("tirana");
        delete.setTo("rome");
        delete.setDepartureDate(Date.valueOf("2021-12-25"));
        delete.setArrivalDate(Date.valueOf("2021-12-25"));
        delete.setStatus(CREATED);

        String loggedUsername = "john";
        User userToPatch = new User();
        userToPatch.setTrips(List.of(delete));

        given(userService.getByUsername(loggedUsername)).willReturn(userToPatch);

        //when
        underTest.delete(loggedUsername, tripId);

        //then
        ArgumentCaptor<Trip> tripArgumentCaptor =
                ArgumentCaptor.forClass(Trip.class);

        verify(tripService).delete(tripArgumentCaptor.capture());

        Trip capturedTrip = tripArgumentCaptor.getValue();

        assertThat(capturedTrip).isEqualTo(delete);
    }
}