package com.example.demoReactiveGw.controller;

import com.example.demoReactiveGw.config.DynamicTlsNettyFactory;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class TestController {

    @Autowired
    DynamicTlsNettyFactory dynamicTlsNettyFactory;


    @SneakyThrows
    @RequestMapping("/test/updateMtls")
    public String tMtls(@RequestHeader HttpHeaders headers, ServerWebExchange exchange) {


        var strInput = resolveBodyFromRequest(exchange.getRequest());

        log.info("input body : {} ",strInput);
        var cacert = headers.getFirst("cacert");
        var cert = headers.getFirst("cert");
        var key = headers.getFirst("key");

        log.info("---- cacert:{}, cert:{}, key:{} ",cacert, cert , key);
        Map<String,String> result = Map.of("cacert",cacert,"cert",cert,"key",key);
        Gson gson = new Gson();

//        List<String> chiper = List.of("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
//                "TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256",
//                "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
//                "TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256",
//                "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256",
//                "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
//                "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
//                "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
//                "TLS_RSA_WITH_AES_128_GCM_SHA256",
//                "TLS_RSA_WITH_AES_128_CBC_SHA256",
//                "TLS_RSA_WITH_AES_128_CBC_SHA",
//                "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
//                "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384",
//                "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
//                "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
//                "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
//                "TLS_RSA_WITH_AES_256_GCM_SHA384",
//                "TLS_RSA_WITH_AES_256_CBC_SHA256",
//                "TLS_RSA_WITH_AES_256_CBC_SHA",
//                "TLS_RSA_WITH_3DES_EDE_CBC_SHA",
//                "TLS_AES_128_GCM_SHA256",
//                "TLS_AES_256_GCM_SHA384",
//                "TLS_CHACHA20_POLY1305_SHA256");
//        dynamicTlsNettyFactory.updateMtlsSettings(new File(cert), new File(key), new File(cacert), chiper, new String[]{"TLSv1.2","TLSv1.3"} );

        return gson.toJson(result);
    }




    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //Get the request body
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        StringBuilder sb = new StringBuilder();
        var db = body.buffer();

        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            String bodyString2 = new String(bytes, StandardCharsets.UTF_8);
            log.info("===> {}", bodyString2);
            buffer.readPosition(0);
//			DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        return sb.toString();
    }
}