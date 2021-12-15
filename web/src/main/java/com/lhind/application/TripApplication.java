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

}
