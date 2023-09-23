package com.spring.reactiveConsumer;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class SynchronousService {
    private final RestTemplate restTemplate;


    public SynchronousService() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public void getAllProperties() {
        List<?> properties = restTemplate.getForObject("http://localhost:8080//hotels", List.class);
        if (properties == null) return;
        System.out.println("Synchronous Service");
        properties.forEach(System.out::println);
    }
}
