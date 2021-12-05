package com.lhind.application.utility.mapper;


import com.lhind.application.entity.Flight;
import com.lhind.application.utility.model.FlightDto.FlightDto;
import com.lhind.application.utility.model.FlightDto.FlightPatchDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FlightMapper {

    public FlightDto flightToFlightDto(Flight flight) {
        if (flight == null) {
            return null;
        }

        FlightDto flightDto = new FlightDto();

        flightDto.setFrom(flight.getFrom());
        flightDto.setTo(flight.getTo());
        flightDto.setDepartureDate(flight.getDepartureDate());
        flightDto.setArrivalDate(flight.getArrivalDate());

        return flightDto;
    }

    public Flight flightDtoToFlight(FlightDto flightDto) {
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

}
