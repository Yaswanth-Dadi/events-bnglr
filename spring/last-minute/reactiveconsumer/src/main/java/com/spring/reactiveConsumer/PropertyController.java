package com.spring.reactiveConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping(value = "/properties")
public class PropertyController {
    @Autowired
    private AsynchronousReactiveService asynchronousReactiveService;

    @Autowired
    private SynchronousService synchronousService;

    @GetMapping(value = "/sync")
    public void getAllPropertiesSync(){
        synchronousService.getAllProperties();
    }

    @GetMapping(value = "/async")
    public void getAllPropertiesAsync(){
       asynchronousReactiveService.getAllProperties();
    }
}
