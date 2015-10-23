package com.example.models.games.dota2;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class MatchDetail {
    @JsonProperty("match_id")
    private Long matchId;
    @JsonProperty("match_seq_num")
    private Long matchSeqNum;
    @JsonProperty("radiant_win")
    private boolean radiantWin;
    private long duration;
    @JsonProperty("start_time")
    private Date startTime;
    @JsonProperty("tower_status_radiant")
    private int towerStatusRadiant;
    @JsonProperty("tower_status_dire")
    private int towerStatusDire;
    private List<Player> players;
    @JsonProperty("barracks_status_radiant")
    private int barracksStatusRadiant;
    @JsonProperty("barracks_status_dire")
    private int barracksStatusDire;
    private int cluster;
    @JsonProperty("first_blood_time")
    private int firstBloodTime;
    @JsonProperty("lobby_type")
    private int lobbyType;
    @JsonProperty("human_players")
    private int humanPlayers;
    private int leagueid;
    @JsonProperty("positive_votes")
    private int positiveVotes;
    @JsonProperty("negative_votes")
    private int negativeVotes;
    @JsonProperty("game_mode")
    private int gameMode;
    private int engine;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getMatchSeqNum() {
        return matchSeqNum;
    }

    public void setMatchSeqNum(Long matchSeqNum) {
        this.matchSeqNum = matchSeqNum;
    }

    public boolean isRadiantWin() {
        return radiantWin;
    }

    public void setRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getTowerStatusRadiant() {
        return towerStatusRadiant;
    }

    public void setTowerStatusRadiant(int towerStatusRadiant) {
        this.towerStatusRadiant = towerStatusRadiant;
    }

    public int getTowerStatusDire() {
        return towerStatusDire;
    }

    public void setTowerStatusDire(int towerStatusDire) {
        this.towerStatusDire = towerStatusDire;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getBarracksStatusRadiant() {
        return barracksStatusRadiant;
    }

    public void setBarracksStatusRadiant(int barracksStatusRadiant) {
        this.barracksStatusRadiant = barracksStatusRadiant;
    }

    public int getBarracksStatusDire() {
        return barracksStatusDire;
    }

    public void setBarracksStatusDire(int barracksStatusDire) {
        this.barracksStatusDire = barracksStatusDire;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public int getFirstBloodTime() {
        return firstBloodTime;
    }

    public void setFirstBloodTime(int firstBloodTime) {
        this.firstBloodTime = firstBloodTime;
    }

    public int getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(int lobbyType) {
        this.lobbyType = lobbyType;
    }

    public int getHumanPlayers() {
        return humanPlayers;
    }

    public void setHumanPlayers(int humanPlayers) {
        this.humanPlayers = humanPlayers;
    }

    public int getLeagueid() {
        return leagueid;
    }

    public void setLeagueid(int leagueid) {
        this.leagueid = leagueid;
    }

    public int getPositiveVotes() {
        return positiveVotes;
    }

    public void setPositiveVotes(int positiveVotes) {
        this.positiveVotes = positiveVotes;
    }

    public int getNegativeVotes() {
        return negativeVotes;
    }

    public void setNegativeVotes(int negativeVotes) {
        this.negativeVotes = negativeVotes;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }
}
