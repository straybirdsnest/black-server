package org.team10424102.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.team10424102.services.extensions.PostExtension;
import org.team10424102.services.extensions.PostExtensionIdentifier;

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
