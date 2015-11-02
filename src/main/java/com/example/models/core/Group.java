package com.example.models.core;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Blob;

@Entity
@Table(name="tGroup")
public class Group {
    private Integer id;
    private String name;
    private String intro;
    private Blob logo;
    @OneToOne
    private Page page;

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Blob getLogo() {
        return logo;
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
