package com.zoltanaltfatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * @author Zoltan Altfatter
 */
@Configuration
@Profile("secure")
public class SecureWebServiceClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecureWebServiceClientConfig.class);

    @Value("${uefa.ws.endpoint-url}")
    private String endpointUri;

    @Value("${uefa.ws.key-store}")
    private Resource keyStore;

    @Value("${uefa.ws.key-store-password}")
    private String keyStorePassword;

    @Value("${uefa.ws.trust-store}")
    private Resource trustStore;

    @Value("${uefa.ws.trust-store-password}")
    private String trustStorePassword;

    @Bean
    public WebServiceTemplate webServiceTemplate(WebServiceTemplateBuilder builder, Jaxb2Marshaller marshaller) throws Exception {
        return builder
                .setDefaultUri(endpointUri)
                .setMarshaller(marshaller)
                .setUnmarshaller(marshaller)
                .additionalMessageSenders(createWebServiceMessageSender())
                .additionalInterceptors(new CustomClientInterceptor()).build();
    }

    private WebServiceMessageSender createWebServiceMessageSender() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(keyStore.getInputStream(), keyStorePassword.toCharArray());

        LOGGER.info("Loaded keystore: " + keyStore.getURI().toString());
        try {
            keyStore.getInputStream().close();
        } catch (IOException e) {
        }
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(ks, keyStorePassword.toCharArray());

        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(trustStore.getInputStream(), trustStorePassword.toCharArray());
        LOGGER.info("Loaded trustStore: " + trustStore.getURI().toString());
        try {
            trustStore.getInputStream().close();
        } catch (IOException e) {
        }
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(ts);

        HttpsUrlConnectionMessageSender webServiceMessageSender = new HttpsUrlConnectionMessageSender();
        webServiceMessageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
        webServiceMessageSender.setTrustManagers(trustManagerFactory.getTrustManagers());

//      otherwise: java.security.cert.CertificateException: No name matching localhost found
        webServiceMessageSender.setHostnameVerifier((hostname, sslSession) -> {
            if (hostname.equals("localhost")) {
                return true;
            }
            return false;
        });

        return webServiceMessageSender;
    }

}
