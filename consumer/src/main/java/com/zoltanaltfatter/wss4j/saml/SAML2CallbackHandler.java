package com.zoltanaltfatter.wss4j.saml;

import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.crypto.CryptoType;
import org.apache.wss4j.common.saml.SAMLCallback;
import org.apache.wss4j.common.saml.bean.AdviceBean;
import org.apache.wss4j.common.saml.bean.KeyInfoBean;
import org.apache.wss4j.common.saml.bean.SubjectBean;
import org.apache.wss4j.common.saml.bean.Version;
import org.apache.wss4j.common.saml.builder.SAML2Constants;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import java.io.IOException;

/**
 * A Callback Handler implementation for a SAML 2 assertion. By default it creates an
 * authentication assertion using Sender Vouches.
 */
public class SAML2CallbackHandler extends AbstractSAMLCallbackHandler {

    public SAML2CallbackHandler() throws Exception {
        if (certs == null) {
            Crypto crypto = CryptoFactory.getInstance("crypto.properties");
            CryptoType cryptoType = new CryptoType(CryptoType.TYPE.ALIAS);
            cryptoType.setAlias("selfsigned");
            certs = crypto.getX509Certificates(cryptoType);
        }

        subjectName = "uid=joe,ou=people,ou=saml-demo,o=example.com";
        subjectQualifier = "www.example.com";
        confirmationMethod = SAML2Constants.CONF_SENDER_VOUCHES;
    }

    public void handle(Callback[] callbacks)
            throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof SAMLCallback) {
                SAMLCallback callback = (SAMLCallback) callbacks[i];
                callback.setSamlVersion(Version.SAML_20);
                callback.setIssuer(issuer);
                callback.setIssuerFormat(issuerFormat);
                if (conditions != null) {
                    callback.setConditions(conditions);
                }
                callback.setIssuerCrypto(getIssuerCrypto());
                callback.setIssuerKeyName(getIssuerName());
                callback.setIssuerKeyPassword(getIssuerPassword());

                if (getAssertionAdviceElement() != null) {
                    AdviceBean advice = new AdviceBean();
                    advice.getAssertions().add(getAssertionAdviceElement());
                    callback.setAdvice(advice);
                }

                SubjectBean subjectBean =
                        new SubjectBean(
                                subjectName, subjectQualifier, confirmationMethod
                        );
                if (subjectNameIDFormat != null) {
                    subjectBean.setSubjectNameIDFormat(subjectNameIDFormat);
                }
                subjectBean.setSubjectConfirmationData(subjectConfirmationData);
                if (SAML2Constants.CONF_HOLDER_KEY.equals(confirmationMethod)) {
                    try {
                        KeyInfoBean keyInfo = createKeyInfo();
                        subjectBean.setKeyInfo(keyInfo);
                    } catch (Exception ex) {
                        throw new IOException("Problem creating KeyInfo: " +  ex.getMessage());
                    }
                }
                callback.setSubject(subjectBean);
                createAndSetStatement(null, callback);
            } else {
                throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }
        }
    }

    public void setSubjectName(String newSubjectName) {
        this.subjectName = newSubjectName;
    }

}
