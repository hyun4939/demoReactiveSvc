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

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.List;

@Component
public class DynamicTlsNettyFactory extends NettyReactiveWebServerFactory {

    private volatile SslContext sslContext;
    final List<String> chiper = List.of("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
            "TLS_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_RSA_WITH_AES_128_CBC_SHA256",
            "TLS_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_RSA_WITH_AES_256_CBC_SHA256",
            "TLS_RSA_WITH_AES_256_CBC_SHA",
            "TLS_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_AES_128_GCM_SHA256",
            "TLS_AES_256_GCM_SHA384",
            "TLS_CHACHA20_POLY1305_SHA256");
    public DynamicTlsNettyFactory(){

        var cert ="/Volumes/samsungssd/git/opensource/hyun4939/demoReactiveSvc/cert/rootca/ti.p12";
        var certPass = "hanati";
        var key ="/Volumes/samsungssd/git/opensource/hyun4939/demoReactiveSvc/cert/rootca/ti.key";
        var keyPass = "hanati";
        var cacert ="/Volumes/samsungssd/git/opensource/hyun4939/demoReactiveSvc/cert/rootca/cacerts";
        var cacertPass = "changeit";

        try {
            updateMtlsSettings(cert, certPass, keyPass, cacert, cacertPass, chiper, new String[]{"TLSv1.2","TLSv1.3"} );
        } catch (SSLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public WebServer getWebServer(HttpHandler httpHandler) {
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create().port(18081);
        server = server.secure(spec -> spec.sslContext(sslContext));
        return new NettyWebServer(server, adapter, null,null,null);
    }

    public void updateMtlsSettings(
            String keystorePath,
            String keystorePassword,
            String keyPassword,
            String truststorePath,
            String truststorePassword,
            List<String> ciphers,
            String[] protocols
    ) throws Exception {
        // KeyManager 생성
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(keystorePath)) {
            keyStore.load(fis, keystorePassword.toCharArray());
        }
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keyPassword.toCharArray());

        // TrustManager 생성
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (FileInputStream fis = new FileInputStream(truststorePath)) {
            trustStore.load(fis, truststorePassword.toCharArray());
        }
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);

        SslContextBuilder builder = SslContextBuilder.forServer(kmf)
                .sslProvider(SslProvider.JDK)
                .ciphers(ciphers, SupportedCipherSuiteFilter.INSTANCE)
                .protocols(protocols)
                .clientAuth(ClientAuth.REQUIRE)
                .trustManager(tmf);

        SslContext newContext = builder.build();
        this.sslContext = newContext;
    }

    // 기본 mTLS 설정을 위한 메소드
//    public void setDefaultMtlsSettings(
//            File certFile,
//            File keyFile,
//            File trustCertCollectionFile
//    ) throws SSLException {
//        List<String> defaultCiphers = Arrays.asList(
//                "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
//                "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384",
//                "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
//                "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384"
//        );
//        String[] defaultProtocols = {"TLSv1.2", "TLSv1.3"};
//        updateMtlsSettings(certFile, keyFile, trustCertCollectionFile, defaultCiphers, defaultProtocols);
//    }
}