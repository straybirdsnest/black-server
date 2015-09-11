package com.example.models;

import javax.persistence.*;

/**
 * Created by yy on 9/11/15.
 */
@Entity
@Table(name = "td_college")
public class College {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 255)
    private String name;

    @Column(length = 20)
    private String area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
