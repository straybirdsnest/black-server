package org.team10424102.models;

import com.fasterxml.jackson.annotation.JsonView;
import org.team10424102.config.json.Views;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@Table(name = "T_GAME")
public class Game {
    @Id
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "logo_id")
    private Image logo;

    @Transient
    private String localizedName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(Views.Game.class)
    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    @JsonView(Views.Game.class)
    public String getIdentifier() {
        return this.name;
    }

    @JsonView(Views.Game.class)
    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }
}
