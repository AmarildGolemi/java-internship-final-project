package com.lhind.application.utility.mapper;


import com.lhind.application.entity.Trip;
import com.lhind.application.utility.model.UserTripDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserTripMapper {

    public UserTripDto userTripToUserTripDto(Trip trip) {
        if (trip == null) {
            return null;
        }

        UserTripDto tripDto = new UserTripDto();

        tripDto.setTripReason(trip.getTripReason());
        tripDto.setDescription(trip.getDescription());
        tripDto.setFrom(trip.getFrom());
        tripDto.setTo(trip.getTo());
        tripDto.setDepartureDate(trip.getDepartureDate());
        tripDto.setArrivalDate(trip.getArrivalDate());

        return tripDto;
    }

    public Trip userTripDtoToUserTrip(UserTripDto userTripDto) {
        if (userTripDto == null) {
            return null;
        }

        Trip trip = new Trip();

        trip.setTripReason(userTripDto.getTripReason());
        trip.setDescription(userTripDto.getDescription());
        trip.setFrom(userTripDto.getFrom());
        trip.setTo(userTripDto.getTo());
        trip.setDepartureDate(userTripDto.getDepartureDate());
        trip.setArrivalDate(userTripDto.getArrivalDate());

        return trip;
    }

}
