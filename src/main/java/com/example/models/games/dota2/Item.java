package com.example.models.games.dota2;

import com.example.models.Image;

/**
 * Created by yy on 9/11/15.
 */
public class Item {
    private Long itemId;
    private String name;
    private Image icon;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
}
