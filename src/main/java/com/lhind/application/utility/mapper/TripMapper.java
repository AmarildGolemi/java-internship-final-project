package com.lhind.application.utility.mapper;

import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.tripdto.TripCreateDto;
import com.lhind.application.utility.model.tripdto.TripDto;
import com.lhind.application.utility.model.tripdto.TripPatchDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TripMapper {

    public TripDto tripToTripDto(Trip trip) {
        if (trip == null) {
            return null;
        }

        TripDto tripDto = new TripDto();

        tripDto.setId(trip.getId());
        tripDto.setTripReason(trip.getTripReason());
        tripDto.setDescription(trip.getDescription());
        tripDto.setFrom(trip.getFrom());
        tripDto.setTo(trip.getTo());
        tripDto.setDepartureDate(trip.getDepartureDate());
        tripDto.setArrivalDate(trip.getArrivalDate());

        return tripDto;
    }

    public Trip tripDtoToTrip(TripCreateDto tripDto) {
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

    public List<TripDto> tripToTripDto(List<Trip> trips) {
        return trips.stream()
                .map(TripMapper::tripToTripDto)
                .collect(Collectors.toList());
    }


}
