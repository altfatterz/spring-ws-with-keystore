package com.zoltanaltfatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

@Configuration
@Profile("http")
public class HttpWebServiceClientConfig {

    @Value("${uefa.ws.endpoint-url}")
    private String endpointUri;

    @Autowired
    private Wss4jSecurityInterceptor wss4jSecurityInterceptor;

    @Autowired
    private LoggingClientInterceptor loggingClientInterceptor;

    @Bean
    public WebServiceTemplate webServiceTemplate(WebServiceTemplateBuilder builder, Jaxb2Marshaller marshaller) {
        return builder
                .setDefaultUri(endpointUri)
                .setMarshaller(marshaller)
                .setUnmarshaller(marshaller)
                .additionalInterceptors(wss4jSecurityInterceptor, loggingClientInterceptor).build();
    }

}
