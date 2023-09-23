package com.spring.reactiveConsumer;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsynchronousReactiveService {
    private final WebClient webClient;

    public AsynchronousReactiveService() {
        this.webClient = WebClient.create("http://localhost:8080/hotels/stream");
    }

    public void getAllProperties() {
        Flux<Property> propertyFlux = webClient.get().accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(Property.class);
        propertyFlux.log().subscribe(new CustomSubscription<>());
    }
}
