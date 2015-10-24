package com.example.models;

import javax.persistence.*;

/**
 * Created by yy on 9/11/15.
 */
@Entity
@Table(name = "tdCollege")
public class College {

    @Id
    @GeneratedValue
    private int collegeId;

    private String collegeName;

    @Column(length = 20)
    private String area;

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int id) {
        this.collegeId = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
