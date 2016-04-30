package com.zoltanaltfatter;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.SERVER)
public class TeamNotFoundException extends Exception {

    public TeamNotFoundException(String message) {
        super(message);
    }

}
