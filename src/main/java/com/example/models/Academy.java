package com.example.models;

import com.example.config.jsonviews.UserView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
public class Academy {
    @Id
    @GeneratedValue
    private Integer id;
    @JsonView(UserView.Profile.class)
    private String name;

    @ManyToOne
    @JoinColumn(name = "logo_id")
    private Image logo;

    @ManyToOne
    @JoinColumn(name = "college_id")
    @JsonIgnore
    private College college;

    //<editor-fold desc="=== Getters & Setters ===">

    public Integer getId() {
        return id;
    }

    public void setId(Integer acadamyId) {
        this.id = acadamyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    //</editor-fold>
}
