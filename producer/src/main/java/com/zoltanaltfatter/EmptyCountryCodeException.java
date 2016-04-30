package com.zoltanaltfatter;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class EmptyCountryCodeException extends Exception {

    public EmptyCountryCodeException(String message) {
        super(message);
    }
}
