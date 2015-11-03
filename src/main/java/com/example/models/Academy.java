package com.example.models;

import javax.persistence.*;

@Entity
public class Academy {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] logo;

    @OneToOne
    @JoinColumn(name = "college_id")
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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
