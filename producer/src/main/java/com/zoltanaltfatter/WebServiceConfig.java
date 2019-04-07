package com.zoltanaltfatter;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;
import java.util.Properties;

/**
 * @author Zoltan Altfatter
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/uefaeuro2016/*");
    }

    @Bean(name = "teams")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema teamSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("TeamsPort");
        wsdl11Definition.setLocationUri("/uefaeuro2016");
        wsdl11Definition.setTargetNamespace("http://www.uefa.com/uefaeuro/season=2016/teams");
        wsdl11Definition.setSchema(teamSchema);
        return wsdl11Definition;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor(){
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setValidationActions("Timestamp UsernameToken");
        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());
        return securityInterceptor;
    }

    @Bean
    public SimplePasswordValidationCallbackHandler securityCallbackHandler(){
        SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
        Properties users = new Properties();
        users.setProperty("admin", "secret");
        callbackHandler.setUsers(users);
        return callbackHandler;
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
//            Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
//            wss4jSecurityInterceptor.setValidationActions("Timestamp SAMLTokenSigned Signature");
//            wss4jSecurityInterceptor.setValidationSignatureCrypto(cryptoFactoryBean.getObject());
//            return wss4jSecurityInterceptor;
//        } catch (Exception e) {
//            throw new RuntimeException("error configuring Wss4jSecurityInterceptor");
//        }
//    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("teams.xsd"));
    }

}
