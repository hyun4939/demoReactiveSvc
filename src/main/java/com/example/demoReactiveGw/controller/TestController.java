package com.example.demoReactiveGw.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class TestController {

//    private final WebClient client;
//
//    // Spring Boot auto-configures a `WebClient.Builder` instance with nice defaults and customizations.
//    // We can use it to create a dedicated `WebClient` for our component.
//    public TestController(WebClient.Builder builder) {
//        this.client = builder.baseUrl("http://localhost:8080").build();
//    }
//
//    public Mono<String> getMessage() {
//        return this.client.get().uri("/hsvc/test").accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .retrieve()
//                .bodyToMono(TestController.class)
//                .map(TestController::getMessage);
//    }

//    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/kkktest")
    public Mono<Map<String,String>> bulkInsert(@RequestBody Flux<Map<String,String>> request) {

        return Mono.just(Map.of("testkey","vvv"));
    }

}