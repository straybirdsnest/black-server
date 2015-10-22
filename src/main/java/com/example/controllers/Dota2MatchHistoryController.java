package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Dota2MatchHistoryController {

    @RequestMapping(value = "/api/dota2matches/once")
    public String getOneMatch(){
        RestTemplate restTemplate = new RestTemplate();
        long matchId = 1883980985;
        String apiKey = "E99AF3581DED23A49576EB68DACBC4A4";
        String url = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?match_id="
                +matchId
                +"&key="
                +apiKey;
        String result = restTemplate.getForObject(url,String.class);
        return result;
    }
}
