package com.zoltanaltfatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@Profile("unsecure")
public class UnsecureWebServiceClientConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecureWebServiceClientConfig.class);

    @Value("${uefa.ws.endpoint-url}")
    private String url;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.eufa.euro");
        return marshaller;
    }

    @Bean
    public TeamClient weatherClient(Jaxb2Marshaller marshaller) throws Exception {
        TeamClient client = new TeamClient();
        client.setDefaultUri(this.url);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }

}
