package com.example.lab2;

import com.example.lab2.service.FlightControllerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DemoRunner {

    @Bean
    CommandLineRunner run(FlightControllerService fcService) {
        return args -> {
            // Пробная вставка (если такого имени не было)
            try {
                fcService.create("Test FC", "Demo", new BigDecimal("9999.99"));
            } catch (Exception ignored) {}
            System.out.println("FC count = " + fcService.getAll().size());
        };
    }
}