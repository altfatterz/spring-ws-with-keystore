package com.zoltanaltfatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;

@Slf4j
public class SoapMessagePrettyPrinter {

    public static void log(WebServiceMessage message) {

        if (message instanceof SaajSoapMessage) {
            SOAPMessage soapMessage = ((SaajSoapMessage) message).getSaajMessage();

            try {
                Source source = soapMessage.getSOAPPart().getContent();

                Transformer transformer = createTransformer();
                ByteArrayOutputStream formattedMessage = new ByteArrayOutputStream();
                transformer.transform(source, new StreamResult(formattedMessage));

                log.info("\n" + formattedMessage.toString());

            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (SOAPException e) {
                e.printStackTrace();
            }

        }
    }

    private static Transformer createTransformer() throws TransformerConfigurationException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        return transformer;
    }

}
