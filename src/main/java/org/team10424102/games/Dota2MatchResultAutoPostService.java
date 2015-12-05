package org.team10424102.games;

import org.team10424102.models.User;

public interface Dota2MatchResultAutoPostService {

    void createSteamAccount(Dota2SteamAccount account);

    void deleteSteamAccount(Dota2SteamAccount account);

    void setAutoPostForUser(User user, String extra);

    void unsetAutoPostForUser(User user);

    boolean hasBindedSteamAccount(User user);

}
