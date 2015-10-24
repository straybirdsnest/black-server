package com.example.models;

import javax.persistence.*;

/**
 * Created by yy on 9/11/15.
 */
@Entity
@Table(name = "tdAcademy")
public class Academy {

    @Id
    @GeneratedValue
    private int academyId;

    private String academyName;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private College college;

    public int getAcademyId() {
        return academyId;
    }

    public void setAcademyId(int acadamyId) {
        this.academyId = acadamyId;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}
