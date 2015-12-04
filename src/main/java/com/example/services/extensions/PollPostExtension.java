package com.example.services.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@PostExtensionIdentifier("poll")
@Component
public class PollPostExtension implements PostExtension{

    @Autowired PollRepo pollRepo;

    @Override
    public Object getData(String stub) {
        Long id = Long.parseLong(stub);
        Poll poll = pollRepo.findOne(id);
        return new PollResult(poll);
    }
}
