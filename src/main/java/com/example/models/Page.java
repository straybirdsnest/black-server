package com.example.models;

import javax.persistence.*;

@Entity
@Table(name = "tPage")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int")
    private Integer id;
    @OneToOne(mappedBy = "page")
    private Group group;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
