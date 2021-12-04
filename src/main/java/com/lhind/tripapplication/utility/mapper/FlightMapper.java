package com.lhind.tripapplication.utility.mapper;


import com.lhind.tripapplication.entity.Flight;
import com.lhind.tripapplication.utility.model.FlightDto;
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

}
