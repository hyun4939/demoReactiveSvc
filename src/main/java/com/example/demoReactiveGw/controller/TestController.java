package com.example.demoReactiveGw.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
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

}