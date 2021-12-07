package com.lhind.application;

import com.lhind.application.entity.Flight;
import com.lhind.application.service.FlightService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TripApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(FlightService flightService){
//        return args -> {
//
//            List<String> cities = Arrays.asList("tirana", "rome", "milan", "florence", "athens", "zurich", "vienna", "paris",
//                    "lyon", "nice", "berlin", "frankfurt", "brussels", "bruges", "amsterdam", "krakow", "london", "madrid", "barcelona");
//
//            System.out.println("Starting...\n");
//
//            int r;
//            for (int i = 0; i < 300; i++) {
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
//                flightService.save(flight);
//            }
//
//            System.out.println("End.\n");
//        };
//    }
}
