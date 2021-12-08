package com.lhind.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TripApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(UserService userService, FlightService flightService){
//        return args -> {
//
//            User john = new User();
//
//            john.setFirstName("john");
//            john.setLastName("doe");
//            john.setPassword("1234");
//            john.setUsername("john");
//            john.setRole(ROLE_ADMIN);
//
//            userService.save(john);
//
//            User jane = new User();
//
//            jane.setFirstName("jane");
//            jane.setLastName("doe");
//            jane.setPassword("1234");
//            jane.setUsername("jane");
//            jane.setRole(ROLE_USER);
//
//            userService.save(jane);
//
//            List<String> cities = Arrays.asList("tirana", "rome", "milan", "florence", "athens", "zurich", "vienna", "paris",
//                    "lyon", "nice", "berlin", "frankfurt", "brussels", "bruges", "amsterdam", "krakow", "london", "madrid", "barcelona");
//
//            List<String> airlines = Arrays.asList("Lufthansa", "Wizz", "Ryanair", "Easyjet", "Turkish Airlines", "Air Serbia",
//                    "Pegas Fly");
//
//            System.out.println("Starting...\n");
//
//            int r;
//            for (int i = 0; i < 200; i++) {
//
//                System.out.println("Adding flight: " + i);
//
//                Flight flight = new Flight();
//                flight.setArrivalDate(Date.valueOf("2021-12-30"));
//                flight.setDepartureDate(Date.valueOf("2021-12-30"));
//
//                r = (int) (Math.random() * cities.size());
//                String from = cities.get(r);
//                flight.setFrom(from);
//
//                r = (int) (Math.random() * cities.size());
//                String to = cities.get(r);
//                flight.setTo(to);
//
//                r = (int) (Math.random() * airlines.size());
//                String airline = airlines.get(r);
//                flight.setAirline(airline);
//
//                flightService.save(flight);
//            }
//
//            System.out.println("End.\n");
//        };
//    }
}
