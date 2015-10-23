package com.example.models;

/**
 * Created by yy on 9/12/15.
 */
public class Role {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
