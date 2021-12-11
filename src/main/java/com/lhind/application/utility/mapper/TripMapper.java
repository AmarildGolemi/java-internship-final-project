package com.lhind.application.utility.mapper;

import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.tripdto.TripPatchDto;
import com.lhind.application.utility.model.tripdto.TripRequestDto;
import com.lhind.application.utility.model.tripdto.TripResponseDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TripMapper {

    public TripResponseDto tripToTripDto(Trip trip) {
        if (trip == null) {
            return null;
        }

        TripResponseDto tripResponseDto = new TripResponseDto();

        tripResponseDto.setId(trip.getId());
        tripResponseDto.setTripReason(trip.getTripReason());
        tripResponseDto.setDescription(trip.getDescription());
        tripResponseDto.setFrom(trip.getFrom());
        tripResponseDto.setTo(trip.getTo());
        tripResponseDto.setDepartureDate(trip.getDepartureDate());
        tripResponseDto.setArrivalDate(trip.getArrivalDate());

        return tripResponseDto;
    }

    public Trip tripDtoToTrip(TripRequestDto tripDto) {
        if (tripDto == null) {
            return null;
        }

        Trip trip = new Trip();

        trip.setTripReason(tripDto.getTripReason());
        trip.setDescription(tripDto.getDescription());
        trip.setFrom(tripDto.getFrom());
        trip.setTo(tripDto.getTo());
        trip.setDepartureDate(tripDto.getDepartureDate());
        trip.setArrivalDate(tripDto.getArrivalDate());

        return trip;
    }

    public Trip tripDtoToTrip(TripPatchDto tripDto) {
        if (tripDto == null) {
            return null;
        }

        Trip trip = new Trip();

        trip.setTripReason(tripDto.getTripReason());
        trip.setDescription(tripDto.getDescription());
        trip.setFrom(tripDto.getFrom());
        trip.setTo(tripDto.getTo());
        trip.setDepartureDate(tripDto.getDepartureDate());
        trip.setArrivalDate(tripDto.getArrivalDate());

        return trip;
    }

    public List<TripResponseDto> tripToTripDto(List<Trip> trips) {
        return trips.stream()
                .map(TripMapper::tripToTripDto)
                .collect(Collectors.toList());
    }


}
