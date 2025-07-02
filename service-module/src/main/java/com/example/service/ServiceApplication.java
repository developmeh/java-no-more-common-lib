package com.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.config.JacksonConfig;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Import(JacksonConfig.class) // Explicitly import the JacksonConfig
public class ServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ServiceApplication.class, args);

        // Print the version of Jackson being used
        ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
        System.out.println("Using Jackson version: " + com.fasterxml.jackson.core.json.PackageVersion.VERSION);
    }

    @RestController
    static class TestController {

        private final ObjectMapper objectMapper;

        TestController(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            // This ObjectMapper is now configured by the service module's JacksonConfig
            // instead of the jackson module's auto-configuration
        }

        @GetMapping("/test")
        public Map<String, Object> test() {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Hello from service!");
            response.put("timestamp", LocalDateTime.now());
            response.put("jacksonVersion", com.fasterxml.jackson.core.json.PackageVersion.VERSION.toString());
            return response;
        }
    }
}
