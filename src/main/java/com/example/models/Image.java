package com.example.models;

import javax.persistence.*;

/**
 * Created by yy on 9/11/15.
 */
@Entity
@Table(name = "tImage")
public class Image {

    @Id
    @Column(columnDefinition="VARCHAR(32)")
    private String id;
    private Type type;
    @Lob
    @Column(columnDefinition = "blob")
    private byte[] data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public enum Type {
        PNG, WEBP, JPEG, BMP
    }
}
