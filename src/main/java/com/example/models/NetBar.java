package com.example.models;

import javax.persistence.*;

@Entity
public class Netbar {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] logo;
    private String location;

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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
