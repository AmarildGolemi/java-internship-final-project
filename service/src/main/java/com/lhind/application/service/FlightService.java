package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.utility.model.flightdto.FlightFilterDto;
import com.lhind.application.utility.model.flightdto.FlightPatchDto;
import com.lhind.application.utility.model.flightdto.FlightRequestDto;
import com.lhind.application.utility.model.flightdto.FlightResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FlightService {

    List<FlightResponseDto> findAll();

    FlightResponseDto findById(Long id);

    Flight getById(Long id);

    List<FlightResponseDto> findFlights(FlightFilterDto flight);

    @Transactional
    FlightResponseDto save(FlightRequestDto flight);

    @Transactional
    FlightResponseDto update(Long id, FlightRequestDto flight);

    @Transactional
    FlightResponseDto patch(Long id, FlightPatchDto flight);

    @Transactional
    String delete(Long id);

}
