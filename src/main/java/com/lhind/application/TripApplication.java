package com.lhind.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TripApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(FlightService flightService){
//        return args -> {
//            Flight flight1 = new Flight();
//            flight1.setArrivalDate(Date.valueOf("2021-12-12"));
//            flight1.setDepartureDate(Date.valueOf("2021-12-12"));
//            flight1.setFrom("tirana");
//            flight1.setTo("paris");
//
//            List<Flight> flightList = flightService.getAvailableFlights(flight1);
//            System.out.println(flightList.size());
//        };
//    }
}
