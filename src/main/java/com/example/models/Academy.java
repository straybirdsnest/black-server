package com.example.models;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "tAcademy")
public class Academy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int")
    private Integer id;
    @Column(columnDefinition = "varchar(30)")
    private String name;

    @Lob
    @Column(columnDefinition = "mediumblob")
    private Blob logo;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "college")
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

    public Blob getLogo() {
        return logo;
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }
}
