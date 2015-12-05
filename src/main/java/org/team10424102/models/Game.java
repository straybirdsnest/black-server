package org.team10424102.models;

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

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }
}
