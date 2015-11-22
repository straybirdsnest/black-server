package com.example.config.security.test;

import com.example.config.security.DomainUser;

import java.util.List;

public interface ServiceGateway {
    List<Stuff> getSomeStuff();

    void createStuff(Stuff newStuff, DomainUser domainUser);
}
