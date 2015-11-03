package com.example.models;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name="tNetbar")
public class NetBar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int")
    private Integer id;
    @Column(columnDefinition = "varchar(30)")
    private String name;
    @Column(columnDefinition = "mediumblob")
    private Blob logo;
    @Column(columnDefinition = "varchar(200)")
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

    public Blob getLogo() {
        return logo;
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
