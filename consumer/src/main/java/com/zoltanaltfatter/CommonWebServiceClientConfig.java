package com.zoltanaltfatter;

import com.zoltanaltfatter.wss4j.saml.SAML2CallbackHandler;
import org.apache.wss4j.common.crypto.Crypto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;

@Configuration
public class CommonWebServiceClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.eufa.euro");
        return marshaller;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor(){
        Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
        wss4jSecurityInterceptor.setSecurementActions("Timestamp UsernameToken");
        wss4jSecurityInterceptor.setSecurementUsername("admin");
        wss4jSecurityInterceptor.setSecurementPassword("secret");
        return wss4jSecurityInterceptor;
    }


//    @Bean
//    public Wss4jSecurityInterceptor securityInterceptor() {
//        try {
//            CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
//            cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("keystore.jks"));
//            cryptoFactoryBean.setKeyStorePassword("password");
//            cryptoFactoryBean.setDefaultX509Alias("selfsigned");
//            cryptoFactoryBean.afterPropertiesSet();
//
//            Crypto crypto = cryptoFactoryBean.getObject();
//
//            SAML2CallbackHandler callbackHandler = new SAML2CallbackHandler();
//            callbackHandler.setIssuer("http://services.spf.hel.kko.ch");
//            callbackHandler.setIssuerName("selfsigned");
//            callbackHandler.setIssuerCrypto(crypto);
//
//
//            Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
//            wss4jSecurityInterceptor.setSecurementActions("Timestamp SAMLTokenSigned");
//            wss4jSecurityInterceptor.setSecurementSignatureCrypto(crypto);
//            wss4jSecurityInterceptor.setSecurementSamlCallbackHandler(callbackHandler);
//            wss4jSecurityInterceptor.setSecurementUsername("selfsigned");
//            wss4jSecurityInterceptor.setSecurementPassword("password");
//
//            return wss4jSecurityInterceptor;
//
//        } catch (Exception e) {
//            throw new RuntimeException("error configuring Wss4jSecurityInterceptor");
//        }
//
//    }


}
