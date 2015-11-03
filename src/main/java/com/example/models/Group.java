package com.example.models;

import javax.persistence.*;

@Entity
public class Group {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String intro;

    @Column(columnDefinition = "mediumblob")
    private byte[] logo;

    @OneToOne
    @JoinColumn(name = "page_id")
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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
