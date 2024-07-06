package com.example.demoReactiveGw.config;

import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.SupportedCipherSuiteFilter;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.stereotype.Component;
import reactor.netty.http.server.HttpServer;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class DynamicTlsNettyFactory extends NettyReactiveWebServerFactory {

    private volatile SslContext sslContext;

    @Override
    public WebServer getWebServer(HttpHandler httpHandler) {
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create();
        server = server.secure(spec -> spec.sslContext(sslContext));
        return new NettyWebServer(server, adapter, null,null,null);
    }

    public void updateMtlsSettings(
            File certFile,
            File keyFile,
            File trustCertCollectionFile,
            List<String> ciphers,
            String[] protocols
    ) throws SSLException {
        SslContextBuilder builder = SslContextBuilder.forServer(certFile, keyFile)
                .sslProvider(SslProvider.JDK) // 또는 SslProvider.OPENSSL 사용 가능
                .ciphers(ciphers, SupportedCipherSuiteFilter.INSTANCE)
                .protocols(protocols)
                .clientAuth(ClientAuth.REQUIRE) // 클라이언트 인증 요구
                .trustManager(trustCertCollectionFile); // 신뢰할 수 있는 클라이언트 인증서 설정

        SslContext newContext = builder.build();
        this.sslContext = newContext;
        // 주의: 이 변경은 새로운 연결에만 적용됩니다.
    }

    // 기본 mTLS 설정을 위한 메소드
    public void setDefaultMtlsSettings(
            File certFile,
            File keyFile,
            File trustCertCollectionFile
    ) throws SSLException {
        List<String> defaultCiphers = Arrays.asList(
                "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
                "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
                "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
                "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384"
        );
        String[] defaultProtocols = {"TLSv1.2", "TLSv1.3"};
        updateMtlsSettings(certFile, keyFile, trustCertCollectionFile, defaultCiphers, defaultProtocols);
    }
}