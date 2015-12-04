package com.example.games;

import com.example.models.User;

public interface Dota2MatchResultAutoPostService {

    void createSteamAccount(Dota2SteamAccount account);

    void deleteSteamAccount(Dota2SteamAccount account);

    void setAutoPostForUser(User user, String extra);

    void unsetAutoPostForUser(User user);

    boolean hasBindedSteamAccount(User user);

}
