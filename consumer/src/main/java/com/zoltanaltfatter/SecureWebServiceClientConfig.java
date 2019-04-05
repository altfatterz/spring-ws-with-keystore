package com.zoltanaltfatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.security.KeyStore;

/**
 * @author Zoltan Altfatter
 */
@Configuration
@Profile("secure")
public class SecureWebServiceClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecureWebServiceClientConfig.class);

    @Value("${uefa.ws.endpoint-url}")
    private String url;

    @Value("${uefa.ws.key-store}")
    private Resource keyStore;

    @Value("${uefa.ws.key-store-password}")
    private String keyStorePassword;

    @Value("${uefa.ws.trust-store}")
    private Resource trustStore;

    @Value("${uefa.ws.trust-store-password}")
    private String trustStorePassword;

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

        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();
        messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
        messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());

//      otherwise: java.security.cert.CertificateException: No name matching localhost found
        messageSender.setHostnameVerifier((hostname, sslSession) -> {
            if (hostname.equals("localhost")) {
                return true;
            }
            return false;
        });

        client.setMessageSender(messageSender);
        return client;
    }

}
