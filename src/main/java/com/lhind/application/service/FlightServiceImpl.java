package com.lhind.application.service;

import com.lhind.application.entity.Flight;
import com.lhind.application.exception.InvalidDateTimeException;
import com.lhind.application.exception.ResourceNotFoundException;
import com.lhind.application.repository.FlightRepository;
import com.lhind.application.utility.mapper.FlightMapper;
import com.lhind.application.utility.model.flightdto.FlightFilterDto;
import com.lhind.application.utility.model.flightdto.FlightPatchDto;
import com.lhind.application.utility.model.flightdto.FlightRequestDto;
import com.lhind.application.utility.model.flightdto.FlightResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public List<FlightResponseDto> findAll() {
        log.info("Finding all flights.");

        List<Flight> flights = flightRepository.findAll();

        checkIfEmpty(flights);

        log.info("Returning the list of flights.");

        return FlightMapper.flightToFlightDto(flights);
    }

    private void checkIfEmpty(List<Flight> flights) {
        if (flights.isEmpty()) {
            log.warn("Zero flights retrieved from the database.");
        }
    }

    @Override
    public FlightResponseDto findById(Long id) {
        log.info("Finding flight by flight id: {}.", id);

        Flight foundFlight = getFlight(id);

        return FlightMapper.flightToFlightDto(foundFlight);
    }

    @Override
    public Flight getById(Long id) {
        log.info("Finding flight by flight id: {}.", id);

        return getFlight(id);
    }

    @Override
    public List<FlightResponseDto> findFlights(FlightFilterDto flight) {
        String from = flight.getFrom();
        String to = flight.getTo();
        String departureDate = flight.getDepartureDate().toString();

        log.info("Retrieving flights from {} to {} on date {}.", from, to, departureDate);

        List<Flight> foundFlights = flightRepository.findFlights(from, to, departureDate);

        return FlightMapper.flightToFlightDto(foundFlights);

    }

    @Override
    public FlightResponseDto save(FlightRequestDto flight) {
        log.info("Saving flight: {}", flight);

        validateFlightRequestDateTime(flight);

        Flight flightToSave = FlightMapper.flightDtoToFlight(flight);

        Flight savedFlight = flightRepository.save(flightToSave);

        return FlightMapper.flightToFlightDto(savedFlight);
    }

    @Override
    public FlightResponseDto update(Long id, FlightRequestDto flight) {
        log.info("Updating flight with id: {}", id);

        validateFlightExists(id);

        validateFlightRequestDateTime(flight);

        Flight flightToUpdate = getFlightToUpdate(id, flight);
        Flight updatedFlight = flightRepository.save(flightToUpdate);

        log.info("Saving updated flight.");

        return FlightMapper.flightToFlightDto(updatedFlight);
    }

    private void validateFlightRequestDateTime(FlightRequestDto flightDto) {
        log.info("Validating flight's date and time are valid.");

        Date departureDate = flightDto.getDepartureDate();
        Time departureTime = flightDto.getDepartureTime();

        Date arrivalDate = flightDto.getArrivalDate();
        Time arrivalTime = flightDto.getArrivalTime();

        validateDateAndTimeAreProvided(departureDate, departureTime, arrivalDate, arrivalTime);

        validateDates(departureDate, arrivalDate);

        validateTime(departureTime, arrivalTime);

    }

    private void validateDateAndTimeAreProvided(Date departureDate, Time departureTime, Date arrivalDate, Time arrivalTime) {
        log.info("Validating both date and times are provided.");

        if (departureDate == null || arrivalDate == null || departureTime == null || arrivalTime == null) {
            log.error("Arrival and departure dates and times are not provided.");

            throw new InvalidDateTimeException();
        }
    }

    private void validateFlightExists(Long id) {
        log.info("Validating flight exists.");

        Optional<Flight> flightOptional = flightRepository.findById(id);

        if (flightOptional.isEmpty()) {
            log.error("No flight found in the database.");

            throw new ResourceNotFoundException();
        }
    }

    private Flight getFlightToUpdate(Long id, FlightRequestDto flight) {
        Flight flightToUpdate = FlightMapper.flightDtoToFlight(flight);
        flightToUpdate.setId(id);

        return flightToUpdate;
    }

    @Override
    public FlightResponseDto patch(Long id, FlightPatchDto flight) {
        log.info("Patching flight with id: {}", id);

        validateFlightPatchDateTime(flight);

        Flight flightToPatch = getFlight(id);

        patchFlight(flight, flightToPatch);

        log.info("Saving updated flight.");

        Flight patchedFlight = flightRepository.save(flightToPatch);

        return FlightMapper.flightToFlightDto(patchedFlight);
    }

    private void validateFlightPatchDateTime(FlightPatchDto flightDto) {
        log.info("Validating flight's patch date and time are valid.");

        Date departureDate = flightDto.getDepartureDate();
        Time departureTime = flightDto.getDepartureTime();

        Date arrivalDate = flightDto.getArrivalDate();
        Time arrivalTime = flightDto.getArrivalTime();

        validateOneDateIsProvided(departureDate, arrivalDate);

        validateOneTimeIsProvided(departureTime, arrivalTime);

        validateDates(departureDate, arrivalDate);

        validateTime(departureTime, arrivalTime);

    }

    private void validateOneTimeIsProvided(Time departureTime, Time arrivalTime) {
        if ((departureTime != null && arrivalTime == null)
                || (departureTime == null && arrivalTime != null)) {
            log.error("One of the times in not provided to patch.");

            throw new ValidationException("Both times should be provided.");
        }
    }

    private void validateOneDateIsProvided(Date departureDate, Date arrivalDate) {
        if ((departureDate != null && arrivalDate == null)
                || (departureDate == null && arrivalDate != null)) {
            log.error("One of the dates in not provided to patch.");

            throw new ValidationException("Both date should be provided.");
        }
    }

    private void validateTime(Time departureTime, Time arrivalTime) {
        log.info("Validating time is valid.");

        if (departureTime != null && arrivalTime != null) {
            Duration timeElapsed = Duration.between(
                    departureTime.toLocalTime(), arrivalTime.toLocalTime());

            if (timeElapsed.isNegative()) {
                log.error("Arrival and departure times are not valid.");

                throw new InvalidDateTimeException();
            }
        }
    }

    private void validateDates(Date departureDate, Date arrivalDate) {
        log.info("Validating dates are valid.");

        if (departureDate != null && arrivalDate != null) {
            Period dateDifference = Period.between(
                    departureDate.toLocalDate(), arrivalDate.toLocalDate());

            if (dateDifference.getDays() < 0) {
                log.error("Arrival and departure dates are not valid.");

                throw new InvalidDateTimeException();
            }
        }
    }

    private void patchFlight(FlightPatchDto flight, Flight flightToPatch) {
        if (flight.getFrom() != null) {
            flightToPatch.setFrom(flight.getFrom());
        }

        if (flight.getTo() != null) {
            flightToPatch.setTo(flight.getTo());
        }

        if (flight.getDepartureDate() != null) {
            flightToPatch.setDepartureDate(flight.getDepartureDate());
        }

        if (flight.getArrivalDate() != null) {
            flightToPatch.setArrivalDate(flight.getArrivalDate());
        }
    }

    @Override
    public String delete(Long id) {
        log.info("Deleting flight with id: {}", id);

        Flight flightToDelete = getFlight(id);

        flightRepository.delete(flightToDelete);

        log.info("Deleting flight with id: {}.", id);

        return "Flight deleted";
    }

    private Flight getFlight(Long id) {
        log.info("Retrieving flight.");

        Optional<Flight> flightOptional = flightRepository.findById(id);

        if (flightOptional.isEmpty()) {
            log.error("No flight found in the database.");

            throw new ResourceNotFoundException();
        }

        log.info("Flight found in the database.");

        return flightOptional.get();
    }


}
