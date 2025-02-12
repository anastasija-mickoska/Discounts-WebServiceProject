package com.example.demo.demo.com.example.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
        return (factory) -> {
            factory.addConnectorCustomizers(connector -> {
                connector.setRedirectPort(8443); // Redirect HTTP to HTTPS
            });
        };
    }
}
