package com.example.models.games.dota2;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by yy on 9/11/15.
 */
@Table(name = "t_dota2_ability")
public class Ability {
    @Id
    private Long abilityId;
    private String name;
    private String localizeName;

    public Long getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(Long abilityId) {
        this.abilityId = abilityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalizeName() {
        return localizeName;
    }

    public void setLocalizeName(String localizeName) {
        this.localizeName = localizeName;
    }
}
