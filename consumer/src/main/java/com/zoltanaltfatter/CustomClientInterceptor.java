package com.zoltanaltfatter;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptorAdapter;
import org.springframework.ws.context.MessageContext;

public class CustomClientInterceptor extends ClientInterceptorAdapter {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        SoapMessagePrettyPrinter.log(messageContext.getRequest());
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        SoapMessagePrettyPrinter.log(messageContext.getResponse());
        return false;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        SoapMessagePrettyPrinter.log(messageContext.getResponse());
        return true;
    }
}
