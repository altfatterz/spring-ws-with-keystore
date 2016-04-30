package com.zoltanaltfatter;

import com.uefa.euro.season.Team;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zoltan Altfatter
 */
@Component
public class TeamRepository {

    private static final List<Team> teams = new ArrayList<Team>();

    @PostConstruct
    public void init() {
        teams.add(create("AL", "Albania", "Shqiponjat (The Eagles)", "Gianni De Biasi"));
        teams.add(create("BE", "Belgium", "Rode Duivels/Diables Rouges (Red Devils)", "Marc Wilmots"));
        teams.add(create("CZ", "Czech Repulic", "Národní tým (National side)", "Pavel Vrba"));
        teams.add(create("FR", "France", "Les Bleus (The Blues)", "Didier Deschamps"));
        teams.add(create("HU", "Hungary", "Mighty Magyars", "Bernd Storck"));
        teams.add(create("IT", "Italy", "Azzurri (Blues)", "Antonio Conte"));
        teams.add(create("PL", "Poland", "Biało-czerwoni (White and reds)", "Adam Nawałka"));
        teams.add(create("IE", "Republic of Ireland", "Boys in Green", "Martin O'Neill"));
        teams.add(create("RU", "Russia", "", "Leonid Slutski"));
        teams.add(create("ES", "Spain", "La Roja (the Reds)", "Vicente del Bosque"));
        teams.add(create("CH", "Switzerland", "Schweizer Nati/Nati Suisse (Swiss national team)", "Vladimir Petković"));
        teams.add(create("UA", "Ukraine", "Synyo-Zhovti (Blue and yellows)", "Mykhailo Fomenko"));
        teams.add(create("AT", "Austria", "", "Marcel Koller"));
        teams.add(create("HR", "Croatia", "Kockasti (Checks)", "Ante Čačić"));
        teams.add(create("GB-ENG", "England", "Three Lions", "Roy Hodgson"));
        teams.add(create("DE", "Germany", "DFB-Elf (DFB eleven)", "Joachim Löw"));
        teams.add(create("IS", "Iceland", "Strákarnir okkar (Our boys)", "Lars Lagerbäck/Heimir Hallgrímsson"));
        teams.add(create("GB-NIR", "Northern Ireland", "Norn Iron", "Michael O'Neill"));
        teams.add(create("PT", "Portugal", "Selecção das Quinas (Team of Shields)", "Fernando Santos"));
        teams.add(create("RO", "Romania", "Tricolorii (The Tricolours)", "Anghel Iordănescu"));
        teams.add(create("SK", "Slovakia", "", "Ján Kozák"));
        teams.add(create("SE", "Sweden", "Blågult (Blue and yellows)", "Erik Hamrén"));
        teams.add(create("TR", "Turkey", "Ay-Yıldızlılar (The Crescent-Stars)", "Fatih Terim"));
        teams.add(create("GB-WLS", "Wales", "Dreigiau (Dragons)", "Chris Coleman"));
    }

    private Team create(String countryCode, String country, String nickName, String coach) {
        Team team = new Team();
        team.setCountryCode(countryCode);
        team.setCountry(country);
        team.setNickName(nickName);
        team.setCoach(coach);
        return team;
    }

    public Team findByCountryCode(String countryCode) {
        Assert.notNull(countryCode);

        for (Team team : teams) {
            if (team.getCountryCode().equals(countryCode)) {
                return team;
            }
        }

        return null;
    }


}
