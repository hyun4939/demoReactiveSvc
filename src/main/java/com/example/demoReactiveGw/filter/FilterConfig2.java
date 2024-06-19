package com.example.demoReactiveGw.filter;


import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class FilterConfig2 {
    @Bean
    public GlobalFilter customGlobalFilter(){
       return new CustomGlobalFilter();
   }

//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder builder) {
////        RouteLocatorBuilder.Builder builder2 = builder.routes();
////        Function<> function = r -> r.path("");
//
//        return builder.routes()
//                .route("cache_request_body_route", r -> r.path("/downstream/**")
//                        .filters(f -> f.prefixPath("/httpbin")
//                                .cacheRequestBody(String.class).uri(uri))
//                        .build();
//    }
}
