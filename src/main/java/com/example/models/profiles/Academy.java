package com.example.models.profiles;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by yy on 9/11/15.
 */
@Entity
@Table(name = "tAcademy")
public class Academy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Blob logo;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private College college;

    public Integer getId() {
        return id;
    }

    public void setId(Integer acadamyId) {
        this.id = acadamyId;
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
