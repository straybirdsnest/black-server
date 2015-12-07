package org.team10424102.blackserver.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.team10424102.blackserver.config.json.Views;
import org.team10424102.blackserver.models.Image;

import javax.persistence.*;

@Entity
public class Dota2Hero {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Image avatar;

    @Transient
    private String heroName;

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

    @JsonProperty("heroAvatar")
    @JsonView(Views.Post.class)
    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    @JsonView(Views.Post.class)
    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
}
