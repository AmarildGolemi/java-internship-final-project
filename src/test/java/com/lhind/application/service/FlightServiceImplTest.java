package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.exception.InvalidDateTimeException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.FlightRepository;
import com.lhind.application.utility.mapper.FlightMapper;
import com.lhind.application.utility.model.flightdto.FlightFilterDto;
import com.lhind.application.utility.model.flightdto.FlightPatchDto;
import com.lhind.application.utility.model.flightdto.FlightRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ValidationException;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

    private FlightService underTest;

    @Mock
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
        underTest = new FlightServiceImpl(flightRepository);
    }

    @Test
    void canFindAll() {
        //when
        underTest.findAll();

        //then
        verify(flightRepository).findAll();
    }

    @Test
    void canFindById() {
        //given
        Long id = 1L;

        given(flightRepository.findById(id)).willReturn(Optional.of(new Flight()));

        //when
        underTest.findById(id);

        //then
        verify(flightRepository).findById(id);
    }

    @Test
    void willThrowWhenUserNotFoundById() {
        //given
        Long id = 1L;

        given(flightRepository.findById(id)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.findById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void canGetById() {
        //given
        Long id = 1L;

        given(flightRepository.findById(id)).willReturn(Optional.of(new Flight()));

        //when
        underTest.getById(id);

        //then
        verify(flightRepository).findById(id);
    }

    @Test
    void willThrowWhenCannotGetFlightById() {
        //given
        Long id = 1L;
        given(flightRepository.findById(id)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.getById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void canFindFlights() {
        //given
        String from = "tirana";
        String to = "berlin";
        String date = "2021-12-25";

        FlightFilterDto flightDto = new FlightFilterDto();
        flightDto.setFrom(from);
        flightDto.setTo(to);
        flightDto.setDepartureDate(Date.valueOf(date));

        //when
        underTest.findFlights(flightDto);

        //then
        verify(flightRepository).findFlights(from, to, date);
    }

    @Test
    void canSaveFlight() {
        //given
        String date = "2021-12-25";

        FlightRequestDto flightDto = new FlightRequestDto();
        flightDto.setFrom("tirana");
        flightDto.setTo("berlin");
        flightDto.setDepartureDate(Date.valueOf(date));
        flightDto.setDepartureTime(Time.valueOf("15:00:00"));
        flightDto.setArrivalDate(Date.valueOf(date));
        flightDto.setArrivalTime(Time.valueOf("17:00:00"));
        flightDto.setAirline("lufthansa");

        Flight flightToSave = FlightMapper.flightDtoToFlight(flightDto);

        //when
        underTest.save(flightDto);

        //then
        ArgumentCaptor<Flight> flightArgumentCaptor =
                ArgumentCaptor.forClass(Flight.class);

        verify(flightRepository).save(flightArgumentCaptor.capture());

        Flight capturedFlight = flightArgumentCaptor.getValue();

        assertThat(capturedFlight).isEqualTo(flightToSave);
    }

    @Test
    void willNotSaveFlightDateTimeNotProvided() {
        //given
        FlightRequestDto flightDto = new FlightRequestDto();
        Flight flightToSave = FlightMapper.flightDtoToFlight(flightDto);

        //when
        //then
        assertThatThrownBy(() -> underTest.save(flightDto))
                .isInstanceOf(InvalidDateTimeException.class);

        verify(flightRepository, never()).save(flightToSave);
    }

    @Test
    void canUpdateFlight() {
        //given
        Long id = 1L;
        String date = "2021-12-25";

        FlightRequestDto flightDto = new FlightRequestDto();
        flightDto.setFrom("tirana");
        flightDto.setTo("berlin");
        flightDto.setDepartureDate(Date.valueOf(date));
        flightDto.setDepartureTime(Time.valueOf("15:00:00"));
        flightDto.setArrivalDate(Date.valueOf(date));
        flightDto.setArrivalTime(Time.valueOf("17:00:00"));
        flightDto.setAirline("lufthansa");

        Flight flightToUpdate = FlightMapper.flightDtoToFlight(flightDto);

        given(flightRepository.findById(id)).willReturn(Optional.of(flightToUpdate));

        //when
        underTest.update(id, flightDto);

        //then
        ArgumentCaptor<Flight> flightArgumentCaptor =
                ArgumentCaptor.forClass(Flight.class);

        verify(flightRepository).save(flightArgumentCaptor.capture());

        Flight capturedFlight = flightArgumentCaptor.getValue();

        assertThat(capturedFlight).isEqualTo(flightToUpdate);
    }

    @Test
    void canPatchFlight() {
        //given
        Long id = 1L;
        String date = "2021-12-25";

        FlightPatchDto flightDto = new FlightPatchDto();
        flightDto.setFrom("tirana");
        flightDto.setTo("berlin");
        flightDto.setDepartureDate(Date.valueOf(date));
        flightDto.setDepartureTime(Time.valueOf("15:00:00"));
        flightDto.setArrivalDate(Date.valueOf(date));
        flightDto.setArrivalTime(Time.valueOf("17:00:00"));

        Flight flightToPatch = FlightMapper.flightDtoToFlight(flightDto);

        given(flightRepository.findById(id)).willReturn(Optional.of(flightToPatch));

        //when
        underTest.patch(id, flightDto);

        //then
        ArgumentCaptor<Flight> flightArgumentCaptor =
                ArgumentCaptor.forClass(Flight.class);

        verify(flightRepository).save(flightArgumentCaptor.capture());

        Flight capturedFlight = flightArgumentCaptor.getValue();

        assertThat(capturedFlight).isEqualTo(flightToPatch);
    }

    @Test
    void willThrowWhenFlightNotFound() {
        //given
        Long id = 1L;
        String date = "2021-12-25";

        FlightRequestDto flightDto = new FlightRequestDto();
        flightDto.setFrom("tirana");
        flightDto.setTo("berlin");
        flightDto.setDepartureDate(Date.valueOf(date));
        flightDto.setDepartureTime(Time.valueOf("15:00:00"));
        flightDto.setArrivalDate(Date.valueOf(date));
        flightDto.setArrivalTime(Time.valueOf("17:00:00"));
        flightDto.setAirline("lufthansa");

        given(flightRepository.findById(id)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.update(id, flightDto))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void willThrowWhenNotBothTimesProvided() {
        //given
        Long id = 1L;

        FlightPatchDto flightDto = new FlightPatchDto();
        flightDto.setDepartureTime(Time.valueOf("15:00:00"));

        Flight flightToPatch = FlightMapper.flightDtoToFlight(flightDto);

        //when
        //then
        assertThatThrownBy(() -> underTest.patch(id, flightDto))
                .isInstanceOf(ValidationException.class);

        verify(flightRepository, never()).save(flightToPatch);
    }

    @Test
    void willThrowWhenDatesNotValid() {
        //given
        Long id = 1L;

        FlightPatchDto flightDto = new FlightPatchDto();
        flightDto.setDepartureDate(Date.valueOf("2021-12-28"));
        flightDto.setArrivalDate(Date.valueOf("2021-12-25"));

        Flight flightToPatch = FlightMapper.flightDtoToFlight(flightDto);

        //when
        //then
        assertThatThrownBy(() -> underTest.patch(id, flightDto))
                .isInstanceOf(InvalidDateTimeException.class);

        verify(flightRepository, never()).save(flightToPatch);
    }

    @Test
    void willThrowWhenNotBothDatesProvided() {
        //given
        Long id = 1L;

        FlightPatchDto flightDto = new FlightPatchDto();
        flightDto.setDepartureDate(Date.valueOf("2021-12-25"));

        Flight flightToPatch = FlightMapper.flightDtoToFlight(flightDto);

        //when
        //then
        assertThatThrownBy(() -> underTest.patch(id, flightDto))
                .isInstanceOf(ValidationException.class);

        verify(flightRepository, never()).save(flightToPatch);
    }

    @Test
    void willThrowWhenTimesNotValid() {
        //given
        Long id = 1L;

        FlightPatchDto flightDto = new FlightPatchDto();
        flightDto.setDepartureTime(Time.valueOf("15:00:00"));
        flightDto.setArrivalTime(Time.valueOf("11:00:00"));

        Flight flightToPatch = FlightMapper.flightDtoToFlight(flightDto);

        //when
        //then
        assertThatThrownBy(() -> underTest.patch(id, flightDto))
                .isInstanceOf(InvalidDateTimeException.class);

        verify(flightRepository, never()).save(flightToPatch);
    }

    @Test
    void canDeleteFlight() {
        // given
        Long id = 1L;
        Flight flight = new Flight();

        given(flightRepository.findById(id)).willReturn(Optional.of(flight));

        // when
        underTest.delete(id);

        // then
        verify(flightRepository).delete(flight);
    }

}