package com.lhind.application.service;

import com.lhind.application.entity.Trip;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

import static com.lhind.application.utility.model.Status.WAITING_FOR_APPROVAL;
import static com.lhind.application.utility.model.TripReason.EVENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {

    private TripService underTest;

    @Mock
    private TripRepository tripRepository;

    @BeforeEach
    void setUp() {
        underTest = new TripServiceImpl(tripRepository);
    }

    @Test
    void canFindTripById() {
        //given
        Long id = 1L;

        Trip trip = new Trip();
        trip.setStatus(WAITING_FOR_APPROVAL);

        given(tripRepository.findById(anyLong())).willReturn(Optional.of(trip));

        //when
        underTest.findById(id);

        //then
        verify(tripRepository).findById(id);
    }

    @Test
    void willThrowWhenTripNotFoundById() {
        //given
        Long id = 1L;
        given(tripRepository.findById(id)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.findById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }


    @Test
    void canFindTripWaitingForApproval() {
        //given
        //when
        underTest.findAllWaitingForApproval();

        //then
        verify(tripRepository).findAllByStatus(WAITING_FOR_APPROVAL);
    }

    @Test
    void canSaveTrip() {
        //given
        Trip tripToSave = new Trip();

        //when
        underTest.save(tripToSave);

        //then
        ArgumentCaptor<Trip> tripArgumentCaptor =
                ArgumentCaptor.forClass(Trip.class);

        verify(tripRepository).save(tripArgumentCaptor.capture());

        Trip capturedTrip = tripArgumentCaptor.getValue();

        assertThat(capturedTrip).isEqualTo(tripToSave);
    }

    @Test
    void canUpdateTrip() {
        //given
        Trip tripToUpdate = new Trip();

        //when
        underTest.update(tripToUpdate);

        //then
        ArgumentCaptor<Trip> tripArgumentCaptor =
                ArgumentCaptor.forClass(Trip.class);

        verify(tripRepository).save(tripArgumentCaptor.capture());

        Trip capturedTrip = tripArgumentCaptor.getValue();

        assertThat(capturedTrip).isEqualTo(tripToUpdate);
    }

    @Test
    void canPatchTrip() {
        //given
        Trip tripToPatch = new Trip();

        Trip trip = new Trip();
        trip.setTripReason(EVENT);
        trip.setDescription("test");
        trip.setFrom("tirana");
        trip.setTo("rome");
        trip.setDepartureDate(Date.valueOf("2021-12-25"));
        trip.setArrivalDate(Date.valueOf("2021-12-25"));

        //when
        underTest.patch(tripToPatch, trip);

        //then
        ArgumentCaptor<Trip> tripArgumentCaptor =
                ArgumentCaptor.forClass(Trip.class);

        verify(tripRepository).save(tripArgumentCaptor.capture());

        Trip capturedTrip = tripArgumentCaptor.getValue();

        assertThat(capturedTrip).isEqualTo(tripToPatch);
    }

    @Test
    void canSendTripForApproval() {
        //given
        Trip tripToSend = new Trip();

        given(tripRepository.findById(tripToSend.getId())).willReturn(Optional.of(tripToSend));

        //when
        underTest.sendForApproval(tripToSend);

        //then
        verify(tripRepository).save(tripToSend);
    }

    @Test
    void willThrowWhenSendingTripForApproval() {
        //given
        Trip tripToSend = new Trip();

        given(tripRepository.findById(tripToSend.getId())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.sendForApproval(tripToSend))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(tripRepository, never()).save(tripToSend);
    }

    @Test
    void canApproveTrip() {
        //given
        Long id = 1L;

        Trip tripToApprove = new Trip();
        tripToApprove.setStatus(WAITING_FOR_APPROVAL);

        given(tripRepository.findById(anyLong())).willReturn(Optional.of(tripToApprove));

        //when
        underTest.approve(id);

        //then
        ArgumentCaptor<Trip> tripArgumentCaptor =
                ArgumentCaptor.forClass(Trip.class);

        verify(tripRepository).save(tripArgumentCaptor.capture());

        Trip capturedTrip = tripArgumentCaptor.getValue();

        assertThat(capturedTrip).isEqualTo(tripToApprove);
    }

    @Test
    void willThrowWhenApprovingTripById() {
        //given
        Long id = 1L;

        Trip tripToApprove = new Trip();

        //when
        //then
        assertThatThrownBy(() -> underTest.approve(id))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(tripRepository, never()).save(tripToApprove);
    }

    @Test
    void willThrowWhenApprovingTripByStatus() {
        //given
        Long id = 1L;

        Trip tripToApprove = new Trip();

        given(tripRepository.findById(anyLong())).willReturn(Optional.of(tripToApprove));

        //when
        //then
        assertThatThrownBy(() -> underTest.approve(id))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(tripRepository, never()).save(tripToApprove);
    }


    @Test
    void canRejectTrip() {
        //given
        Long id = 1L;

        Trip tripToReject = new Trip();
        tripToReject.setStatus(WAITING_FOR_APPROVAL);

        given(tripRepository.findById(id)).willReturn(Optional.of(tripToReject));

        //when
        underTest.reject(id);

        //then
        ArgumentCaptor<Trip> tripArgumentCaptor =
                ArgumentCaptor.forClass(Trip.class);

        verify(tripRepository).save(tripArgumentCaptor.capture());

        Trip capturedTrip = tripArgumentCaptor.getValue();

        assertThat(capturedTrip).isEqualTo(tripToReject);
    }

    @Test
    void willThrowWhenRejectingTripByStatus() {
        //given
        Long id = 1L;

        Trip tripToApprove = new Trip();

        given(tripRepository.findById(anyLong())).willReturn(Optional.of(tripToApprove));

        //when
        //then
        assertThatThrownBy(() -> underTest.approve(id))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(tripRepository, never()).save(tripToApprove);
    }


    @Test
    void willThrowWhenRejectingTrip() {
        //given
        Long id = 1L;

        Trip tripToReject = new Trip();

        //when
        //then
        assertThatThrownBy(() -> underTest.reject(id))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(tripRepository, never()).save(tripToReject);
    }

    @Test
    void delete() {
        //given
        Trip tripToDelete = new Trip();

        //when
        underTest.delete(tripToDelete);

        //then
        verify(tripRepository).delete(tripToDelete);
    }

}