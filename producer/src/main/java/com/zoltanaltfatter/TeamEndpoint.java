package com.zoltanaltfatter;

import com.uefa.euro.season.GetTeamRequest;
import com.uefa.euro.season.GetTeamResponse;
import com.uefa.euro.season.Team;
import org.springframework.util.StringUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * @author Zoltan Altfatter
 */
@Endpoint
public class TeamEndpoint {

    private static final String NAMESPACE_URI = "http://www.uefa.com/uefaeuro/season=2016/teams";

    private TeamRepository teamRepository;

    public TeamEndpoint(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTeamRequest")
    @ResponsePayload
    public GetTeamResponse getTeam(@RequestPayload GetTeamRequest request) throws TeamNotFoundException, EmptyCountryCodeException {
        if (StringUtils.isEmpty(request.getCountryCode())) {
            throw new EmptyCountryCodeException("country code cannot be empty");
        }

        GetTeamResponse response = new GetTeamResponse();
        Team team = teamRepository.findByCountryCode(request.getCountryCode());

        if (team == null) {
            throw new TeamNotFoundException("invalid country code or country did not qualify");
        }

        response.setTeam(team);
        return response;
    }

}
