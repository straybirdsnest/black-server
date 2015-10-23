package com.example.models.games.dota2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdditionalUnit {
    @JsonProperty("unitname")
    private String unitName;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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
}
