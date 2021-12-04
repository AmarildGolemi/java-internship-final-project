package com.lhind.tripapplication.utility.mapper;


import com.lhind.tripapplication.entity.Trip;
import com.lhind.tripapplication.utility.model.TripDto;
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

}
