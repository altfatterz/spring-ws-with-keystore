package com.zoltanaltfatter;

import com.eufa.euro.GetTeamRequest;
import com.eufa.euro.GetTeamResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * @author Zoltan Altfatter
 */
public class TeamClient extends WebServiceGatewaySupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamClient.class);

    public GetTeamResponse getTeamByCountryCode(String countryCode) {
        GetTeamRequest request = new GetTeamRequest();
        request.setCountryCode(countryCode);

        GetTeamResponse response = (GetTeamResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        LOGGER.info("received message:" + response);
        return response;
    }
}
