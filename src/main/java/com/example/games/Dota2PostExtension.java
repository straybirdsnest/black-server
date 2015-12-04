package com.example.games;

import com.example.services.extensions.PostExtension;
import com.example.services.extensions.PostExtensionIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@PostExtensionIdentifier("dota2_match_result")
@Component
public class Dota2PostExtension implements PostExtension {

    @Autowired Dota2MatchResultRepo resultRepo;

    @Override
    public Object getData(String stub) {
        Long id = Long.parseLong(stub);
        Dota2MatchResult result = resultRepo.findOne(id);
        return result;
    }
}
