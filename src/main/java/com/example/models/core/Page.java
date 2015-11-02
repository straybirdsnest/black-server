package com.example.models.core;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by otakuplus on 2015/11/2.
 */
@Entity
@Table(name = "tPage")
public class Page {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
