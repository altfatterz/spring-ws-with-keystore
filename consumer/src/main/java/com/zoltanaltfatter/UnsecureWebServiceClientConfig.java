package com.zoltanaltfatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
@Profile("unsecure")
public class UnsecureWebServiceClientConfig {

    @Value("${uefa.ws.endpoint-url}")
    private String endpointUri;

    @Bean
    public WebServiceTemplate webServiceTemplate(WebServiceTemplateBuilder builder, Jaxb2Marshaller marshaller) {
        return builder
                .setDefaultUri(endpointUri)
                .setMarshaller(marshaller)
                .setUnmarshaller(marshaller)
                .additionalInterceptors(new CustomClientInterceptor()).build();
    }

}
