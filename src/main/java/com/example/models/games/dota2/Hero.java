package com.example.models.games.dota2;

/**
 * Created by yy on 9/11/15.
 */
public class Hero {
    private Long heroId;
    private String name;
    //private Image icon;

    public Long getHeroId() {
        return heroId;
    }

    public void setHeroId(Long heroId) {
        this.heroId = heroId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
    */
}
