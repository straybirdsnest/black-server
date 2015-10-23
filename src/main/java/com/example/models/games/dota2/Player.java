package com.example.models.games.dota2;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by yy on 9/11/15.
 */
public class Player {
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("player_slot")
    private long playerSlot;
    @JsonProperty("hero_id")
    private long heroId;
    @JsonProperty("item_0")
    private long item0;
    @JsonProperty("item_1")
    private long item1;
    @JsonProperty("item_2")
    private long item2;
    @JsonProperty("item_3")
    private long item3;
    @JsonProperty("item_4")
    private long item4;
    @JsonProperty("item_5")
    private long item5;
    private int kills;
    private int deaths;
    private int assists;
    @JsonProperty("leaver_status")
    private int leaverStatus;
    private long gold;
    @JsonProperty("last_hits")
    private long lastHits;
    private long denies;
    @JsonProperty("gold_per_min")
    private long goldPerMin;
    @JsonProperty("xp_per_min")
    private long xpPerMin;
    @JsonProperty("gold_spent")
    private long goldSpent;
    @JsonProperty("hero_damage")
    private long heroDamage;
    @JsonProperty("tower_damage")
    private long towerDamage;
    @JsonProperty("hero_healing")
    private long heroHealing;
    private int level;
    @JsonProperty("ability_upgrades")
    private List<AbilityUpgrade> abilityUpgrades;
    @JsonProperty("additional_units")
    private List<AdditionalUnit> additionalUnits;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(long playerSlot) {
        this.playerSlot = playerSlot;
    }

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public long getItem0() {
        return item0;
    }

    public void setItem0(long item0) {
        this.item0 = item0;
    }

    public long getItem1() {
        return item1;
    }

    public void setItem1(long item1) {
        this.item1 = item1;
    }

    public long getItem2() {
        return item2;
    }

    public void setItem2(long item2) {
        this.item2 = item2;
    }

    public long getItem3() {
        return item3;
    }

    public void setItem3(long item3) {
        this.item3 = item3;
    }

    public long getItem4() {
        return item4;
    }

    public void setItem4(long item4) {
        this.item4 = item4;
    }

    public long getItem5() {
        return item5;
    }

    public void setItem5(long item5) {
        this.item5 = item5;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getLeaverStatus() {
        return leaverStatus;
    }

    public void setLeaverStatus(int leaverStatus) {
        this.leaverStatus = leaverStatus;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getLastHits() {
        return lastHits;
    }

    public void setLastHits(long lastHits) {
        this.lastHits = lastHits;
    }

    public long getDenies() {
        return denies;
    }

    public void setDenies(long denies) {
        this.denies = denies;
    }

    public long getGoldPerMin() {
        return goldPerMin;
    }

    public void setGoldPerMin(long goldPerMin) {
        this.goldPerMin = goldPerMin;
    }

    public long getXpPerMin() {
        return xpPerMin;
    }

    public void setXpPerMin(long xpPerMin) {
        this.xpPerMin = xpPerMin;
    }

    public long getGoldSpent() {
        return goldSpent;
    }

    public void setGoldSpent(long goldSpent) {
        this.goldSpent = goldSpent;
    }

    public long getHeroDamage() {
        return heroDamage;
    }

    public void setHeroDamage(long heroDamage) {
        this.heroDamage = heroDamage;
    }

    public long getTowerDamage() {
        return towerDamage;
    }

    public void setTowerDamage(long towerDamage) {
        this.towerDamage = towerDamage;
    }

    public long getHeroHealing() {
        return heroHealing;
    }

    public void setHeroHealing(long heroHealing) {
        this.heroHealing = heroHealing;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<AbilityUpgrade> getAbilityUpgrades() {
        return abilityUpgrades;
    }

    public void setAbilityUpgrades(List<AbilityUpgrade> ability_pgrades) {
        this.abilityUpgrades = ability_pgrades;
    }

    public List<AdditionalUnit> getAdditionalUnits() {
        return additionalUnits;
    }

    public void setAdditionalUnits(List<AdditionalUnit> additionalUnits) {
        this.additionalUnits = additionalUnits;
    }
}
