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

Import the SoapUI project found in the `soapui` folder

Configure the client certificate (found in the `soapui` folder) in the `SoapUI Preferences -> SSL Settings` with `Keystore` field setting to the path of the soapui.jks file and `KeyStore Password` setting to `password`

Make a `getTeamRequest` using the example sending the SOAP message to `https://localhost:8443/uefaeuro2016`

For more in depth explanation check the blog post:




