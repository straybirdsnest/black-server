package com.example.models;

import javax.persistence.*;

/**
 * Created by yy on 9/11/15.
 */
@Entity
@Table(name = "td_academy")
public class Academy {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private College college;

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

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}
