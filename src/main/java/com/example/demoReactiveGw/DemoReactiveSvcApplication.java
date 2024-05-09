package com.example.demoReactiveGw;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.netty.http.server.HttpServerRequest;

import java.nio.charset.StandardCharsets;


@Slf4j
@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class DemoReactiveSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoReactiveSvcApplication.class, args);
	}

	@RequestMapping("test")
	public String test(){
		return "{\"test\":\"--\"}";
	}

	@SneakyThrows
	@RequestMapping("/hsvc/test")
	public String htest(@RequestHeader HttpHeaders headers, ServerWebExchange exchange) {


		var testtttt = resolveBodyFromRequest(exchange.getRequest());

		log.info(testtttt);
		var test = headers.getFirst("test");
		var test2 = headers.getFirst("test2");

		log.info("---- {} {} ",test, test2 );
		return "tt/hsvc/test/hsvc/test/hsvc/testt";
	}


	private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest){
		//Get the request body
		Flux<DataBuffer> body = serverHttpRequest.getBody();
		StringBuilder sb = new StringBuilder();
		var db = body.buffer();

		body.subscribe(buffer -> {
			byte[] bytes = new byte[buffer.readableByteCount()];
			buffer.read(bytes);
			String bodyString2 = new String(bytes, StandardCharsets.UTF_8);
			log.info("===> {}",bodyString2);
			buffer.readPosition(0);
//			DataBufferUtils.release(buffer);
			String bodyString = new String(bytes, StandardCharsets.UTF_8);
			sb.append(bodyString);
		});



		return sb.toString();

	}
}
