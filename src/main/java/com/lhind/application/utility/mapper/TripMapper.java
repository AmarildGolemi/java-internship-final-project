package com.lhind.application.utility.mapper;


import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.tripdto.TripDto;
import com.lhind.application.utility.model.tripdto.TripPatchDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TripMapper {

    public TripDto tripToTripDto(Trip trip) {
        if (trip == null) {
            return null;
        }

        TripDto tripDto = new TripDto();

        tripDto.setTripReason(trip.getTripReason());
        tripDto.setDescription(trip.getDescription());
        tripDto.setFrom(trip.getFrom());
        tripDto.setTo(trip.getTo());
        tripDto.setDepartureDate(trip.getDepartureDate());
        tripDto.setArrivalDate(trip.getArrivalDate());

        return tripDto;
    }

    public Trip tripDtoToTrip(TripDto tripDto) {
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


}
