package com.example.demo.demo.discounts;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
@EnableWs
@Configuration
public class ServiceConfig {


    
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true); // Ensures correct WSDL path
        return new ServletRegistrationBean<>(servlet, "/ws/*"); // Maps SOAP requests to /ws/
    }

    @Bean(name = "discounts")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema discountsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("DiscountsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example.com/discounts");
        wsdl11Definition.setSchema(discountsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema discountsSchema() {
        return new SimpleXsdSchema(new org.springframework.core.io.ClassPathResource("discounts.xsd"));
    }
}
