package com.lhind.application.utility.mapper;


import com.lhind.application.entity.Flight;
import com.lhind.application.utility.model.flightdto.FlightCreateDto;
import com.lhind.application.utility.model.flightdto.FlightDto;
import com.lhind.application.utility.model.flightdto.FlightFilterDto;
import com.lhind.application.utility.model.flightdto.FlightPatchDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class FlightMapper {

    public FlightDto flightToFlightDto(Flight flight) {
        if (flight == null) {
            return null;
        }

        FlightDto flightDto = new FlightDto();

        flightDto.setId(flight.getId());
        flightDto.setFrom(flight.getFrom());
        flightDto.setTo(flight.getTo());
        flightDto.setDepartureDate(flight.getDepartureDate());
        flightDto.setArrivalDate(flight.getArrivalDate());
        flightDto.setAirline(flight.getAirline());

        return flightDto;
    }

    public Flight flightDtoToFlight(FlightCreateDto flightDto) {
        if (flightDto == null) {
            return null;
        }

        Flight flight = new Flight();

        flight.setFrom(flightDto.getFrom());
        flight.setTo(flightDto.getTo());
        flight.setDepartureDate(flightDto.getDepartureDate());
        flight.setArrivalDate(flightDto.getArrivalDate());
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
        flight.setArrivalDate(flightDto.getArrivalDate());

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

    public List<FlightDto> flightToFlightDto(List<Flight> flights) {
        return flights.stream()
                .map(FlightMapper::flightToFlightDto)
                .collect(Collectors.toList());

    }

}
