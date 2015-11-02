package com.example.models.games.dota2;

import com.example.models.core.User;

import javax.persistence.*;

//@Entity
@Table(name = "tSteamAccount")
public class SteamAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameAccountId;
    private long steamUserId;
    @ManyToOne()
    private User owner;

    public Long getGameAccountId() {
        return gameAccountId;
    }

    public void setGameAccountId(Long gameAccountId) {
        this.gameAccountId = gameAccountId;
    }

    public long getSteamUserId() {
        return steamUserId;
    }

    public void setSteamUserId(long steamUserId) {
        this.steamUserId = steamUserId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
