package com.lhind.application;

import com.lhind.application.entity.Flight;
import com.lhind.application.entity.Role;
import com.lhind.application.entity.User;
import com.lhind.application.repository.FlightRepository;
import com.lhind.application.repository.UserRepository;
import com.lhind.application.service.RoleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TripApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(UserRepository userRepository, RoleService roleService, FlightRepository flightRepository){
//        return args -> {
//
//            Role admin = getRoleAdmin(roleService);
//
//            Role user = getRoleUser(roleService);
//
//            createUsers(userRepository, admin, user);
//
//            createDummyFlights(flightRepository, "2021-12-25");
//            createDummyFlights(flightRepository, "2021-12-30");
//
//        };
//    }

    private Role getRoleUser(RoleService roleService) {
        Role user = new Role();
        user.setName("ROLE_USER");
        roleService.save(user);
        return user;
    }

    private Role getRoleAdmin(RoleService roleService) {
        Role admin = new Role();
        admin.setName("ROLE_ADMIN");
        roleService.save(admin);
        return admin;
    }

    private void createUsers(UserRepository userRepository, Role admin, Role user) {
        User john = new User();
        john.setFirstName("john");
        john.setLastName("doe");
        john.setPassword("1234");
        john.setUsername("john");
        john.setRoles(List.of(admin));
        userRepository.save(john);

        User jane = new User();
        jane.setFirstName("jane");
        jane.setLastName("doe");
        jane.setPassword("1234");
        jane.setUsername("jane");
        jane.setRoles(List.of(user));
        userRepository.save(jane);
    }

    private void createDummyFlights(FlightRepository flightRepository, String date) {

        Random random = new Random();

        List<String> cities = Arrays.asList("tirana", "rome", "milan", "florence", "athens", "zurich", "vienna", "paris",
                "lyon", "nice", "berlin", "frankfurt", "brussels", "bruges", "amsterdam", "krakow", "london", "madrid", "barcelona");

        List<String> airlines = Arrays.asList("Lufthansa", "Wizz", "Ryanair", "Easyjet", "Turkish Airlines", "Air Serbia",
                "Pegas Fly");

        System.out.println("Starting...\n");

        int r;
        for (int i = 0; i < 1000; i++) {

            System.out.println("Adding flight: " + i);

            final int millisInDay = 24 * 60 * 60 * 1000;
            Time departureTime = new Time(random.nextInt(millisInDay));
            departureTime.setSeconds(0);
            Time arrivalTime = new Time(random.nextInt(millisInDay));
            arrivalTime.setSeconds(0);

            Duration timeElapsed = Duration.between(departureTime.toLocalTime(), arrivalTime.toLocalTime());

            if (timeElapsed.isNegative() || timeElapsed.toHours() > 5 || timeElapsed.toHours() < 1)
                continue;

            r = (int) (Math.random() * cities.size());
            String from = cities.get(r);

            r = (int) (Math.random() * cities.size());
            String to = cities.get(r);

            if (from.equals(to)) {
                continue;
            }

            Flight flight = new Flight();
            flight.setArrivalDate(Date.valueOf(date));
            flight.setDepartureDate(Date.valueOf(date));
            flight.setDepartureTime(departureTime);
            flight.setArrivalTime(arrivalTime);
            flight.setFrom(from);
            flight.setTo(to);

            r = (int) (Math.random() * airlines.size());
            String airline = airlines.get(r);
            flight.setAirline(airline);

            flightRepository.save(flight);
        }

        System.out.println("End.\n");
    }

}
