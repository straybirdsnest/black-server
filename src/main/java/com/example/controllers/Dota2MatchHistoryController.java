package com.example.controllers;

import com.example.models.games.dota2.Dota2APIResult;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Dota2MatchHistoryController {

    @RequestMapping(value = "/api/dota2matches/once")
    public @ResponseBody Dota2APIResult getOneMatch(){
        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        // Add the Jackson message converter
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        long matchId = 1883980985;
        String apiKey = "E99AF3581DED23A49576EB68DACBC4A4";
        String url = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?match_id="
                +matchId
                +"&key="
                +apiKey;

        // Make the HTTP GET request, marshaling the response from JSON to MatchDetail
        Dota2APIResult dota2APIResult = restTemplate.getForObject(url, Dota2APIResult.class);
        /*
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse
        Dota2APIResult dota2APIResult = null;
        MatchDetail matchDetail = null;
        try {
            matchDetail = mapper.readValue(result, Dota2APIResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        return dota2APIResult;
    }
}
