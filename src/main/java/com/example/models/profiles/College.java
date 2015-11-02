package com.example.models.profiles;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by yy on 9/11/15.
 */
@Entity
@Table(name = "tCollege")
public class College {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String nameExt;

    private Blob logo;

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

    public String getNameExt() {
        return nameExt;
    }

    public void setNameExt(String nameExt) {
        this.nameExt = nameExt;
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
