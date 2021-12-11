package com.lhind.application.utility.mapper;


import com.lhind.application.entity.Flight;
import com.lhind.application.utility.model.flightdto.*;
import lombok.experimental.UtilityClass;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class FlightMapper {

    public FlightResponseDto flightToFlightDto(Flight flight) {
        if (flight == null) {
            return null;
        }

        FlightResponseDto flightResponseDto = new FlightResponseDto();

        flightResponseDto.setId(flight.getId());
        flightResponseDto.setFrom(flight.getFrom());
        flightResponseDto.setTo(flight.getTo());
        flightResponseDto.setDepartureDate(flight.getDepartureDate());
        flightResponseDto.setDepartureTime(flight.getDepartureTime());
        flightResponseDto.setArrivalDate(flight.getArrivalDate());
        flightResponseDto.setArrivalTime(flight.getArrivalTime());
        flightResponseDto.setAirline(flight.getAirline());

        return flightResponseDto;
    }

    public Flight flightDtoToFlight(FlightRequestDto flightDto) {
        if (flightDto == null) {
            return null;
        }

        Flight flight = new Flight();

        flight.setFrom(flightDto.getFrom());
        flight.setTo(flightDto.getTo());
        flight.setDepartureDate(flightDto.getDepartureDate());

        Time departureTime = flightDto.getDepartureTime();
        flight.setDepartureTime(departureTime);

        flight.setArrivalDate(flightDto.getArrivalDate());

        Time arrivalTime = flightDto.getArrivalTime();
        flight.setArrivalTime(arrivalTime);

        flight.setAirline(flightDto.getAirline());

        return flight;
    }

    public Flight flightDtoToFlight(FlightPatchDto flightDto) {
        if (flightDto == null) {
            return null;
        }

        Flight flight = new Flight();

        flight.setFrom(flightDto.getFrom());
        flight.setTo(flightDto.getTo());
        flight.setDepartureDate(flightDto.getDepartureDate());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalDate(flightDto.getArrivalDate());
        flight.setArrivalTime(flightDto.getArrivalTime());

        return flight;
    }

    public Flight flightDtoToFlight(FlightFilterDto flightDto) {
        if (flightDto == null) {
            return null;
        }

        Flight flight = new Flight();

        flight.setFrom(flightDto.getFrom());
        flight.setTo(flightDto.getTo());
        flight.setDepartureDate(flightDto.getDepartureDate());

        return flight;
    }

    public List<FlightResponseDto> flightToFlightDto(List<Flight> flights) {
        return flights.stream()
                .map(FlightMapper::flightToFlightDto)
                .collect(Collectors.toList());

    }

}
