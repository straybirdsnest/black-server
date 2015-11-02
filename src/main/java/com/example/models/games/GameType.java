package com.example.models.games;

import javax.persistence.*;

@Entity
@Table(name = "tdGameType")
public class GameType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameTypeId;
    private String gameName;

    public Long getGameTypeId() {
        return gameTypeId;
    }

    public void setGameTypeId(Long gameTypeId) {
        this.gameTypeId = gameTypeId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
