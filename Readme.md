# SOAP over HTTPS with client certificate authentication

```
$ git clone https://github.com/altfatterz/spring-ws-with-keystore
$ cd spring-ws-with-keystore
$ mvn clean package
```

Start the uefa-service with:

```
$ java -jar producer/target/producer-0.0.1-SNAPSHOT.jar --spring.profiles.active=secure
```

#### SoapUI

Import the SoapUI project found in the `soapui` folder

Configure the client certificate (found in the `soapui` folder) in the `SoapUI Preferences -> SSL Settings` with `Keystore` field setting to the path of the `soapui.jks` file and `KeyStore Password` setting to `password`

Make a `getTeamRequest` using the example sending the SOAP message to `https://localhost:8443/uefaeuro2016`


#### java client

Start the client with

```
java -jar consumer/target/consumer-0.0.1-SNAPSHOT.jar
```

In the output you should see:

```
2016-04-30 16:48:25.715 DEBUG 69021 --- [           main] .s.w.t.h.HttpsUrlConnectionMessageSender : Initialized SSL Context with key managers [sun.security.ssl.SunX509KeyManagerImpl@5e853265] trust managers [sun.security.ssl.X509TrustManagerImpl@67205a84] secure random [null]
2016-04-30 16:48:25.724 DEBUG 69021 --- [           main] o.s.ws.client.core.WebServiceTemplate    : Opening [org.springframework.ws.transport.http.HttpUrlConnection@7d0587f1] to [https://localhost:8443/uefaeuro2016]
2016-04-30 16:48:25.760 DEBUG 69021 --- [           main] o.s.ws.client.MessageTracing.sent        : Sent request [SaajSoapMessage {http://www.uefa.com/uefaeuro/season=2016/teams}getTeamRequest]
2016-04-30 16:48:26.352 DEBUG 69021 --- [           main] o.s.ws.client.MessageTracing.received    : Received response [SaajSoapMessage {http://www.uefa.com/uefaeuro/season=2016/teams}getTeamResponse] for request [SaajSoapMessage {http://www.uefa.com/uefaeuro/season=2016/teams}getTeamRequest]
2016-04-30 16:48:26.357  INFO 69021 --- [           main] com.zoltanaltfatter.TeamClient           : received message:com.eufa.euro.GetTeamResponse@687080dc
2016-04-30 16:48:26.357  INFO 69021 --- [           main] com.zoltanaltfatter.Client               : countryCode: 'HU', country: 'Hungary', team nick name:'Mighty Magyars', coach:'Bernd Storck'
```

For more in-depth explanation check the blog post:




