package com.zoltanaltfatter;

import com.eufa.euro.GetTeamRequest;
import com.eufa.euro.GetTeamResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * @author Zoltan Altfatter
 */
@Slf4j
@Component
public class TeamClient {

    private WebServiceTemplate webServiceTemplate;

    public TeamClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public GetTeamResponse getTeamByCountryCode(String countryCode) {
        GetTeamRequest request = new GetTeamRequest();
        request.setCountryCode(countryCode);

        GetTeamResponse response = (GetTeamResponse) webServiceTemplate.marshalSendAndReceive(request);

        log.info("received message:" + response);
        return response;
    }
}
