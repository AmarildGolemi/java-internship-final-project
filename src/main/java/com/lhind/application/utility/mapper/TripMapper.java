package com.lhind.application.utility.mapper;


import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.TripDto;
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
        tripDto.setUserId(trip.getUser().getId());

        return tripDto;
    }

}
