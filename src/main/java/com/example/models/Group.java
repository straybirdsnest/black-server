package com.example.models;

import javax.persistence.*;

@Entity
public class Group {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String intro;

    @ManyToOne
    @JoinColumn(name = "logo_id")
    private Image logo;

    @OneToOne
    @JoinColumn(name = "page_id")
    private Page page;

    @Transient
    private String logoAccessToken;

    //<editor-fold desc="=== Getters & Setters ===">

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

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getPageId(){
        if(page != null){
            return page.getId();
        }
        return null;
    }

    public String getLogoAccessToken() {
        return logoAccessToken;
    }

    public void setLogoAccessToken(String logoAccessToken) {
        this.logoAccessToken = logoAccessToken;
    }
//</editor-fold>
}
