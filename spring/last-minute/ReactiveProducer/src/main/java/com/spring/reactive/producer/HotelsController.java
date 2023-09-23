package com.spring.reactive.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/hotels")
public class HotelsController {
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getHotels() {
        List<Hotel> hotels = new ArrayList<>();
        hotelRepository.findAll().forEach(hotelEntity ->
                {
                    hotels.add(new Hotel(hotelEntity.getId(), hotelEntity.getName()));

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + " --> SOME UNEXPECTED EXCEPTION FROM THE THREAD SLEEP");
                    }
                }
        );
        return hotels;
    }

    @GetMapping(value = "/stream")
    public Flux<Hotel> getHotelsStream() {
        return Flux.fromIterable(hotelRepository.findAll())
                .delayElements(Duration.ofSeconds(1))
                .map(itr -> new Hotel(itr.getId(), itr.getName()));
    }

}
