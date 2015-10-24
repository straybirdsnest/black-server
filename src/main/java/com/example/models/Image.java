package com.example.models;

import javax.persistence.*;

/**
 * Created by yy on 9/11/15.
 */
@Entity
@Table(name = "tImage")
public class Image {

    @Id
    @Column(columnDefinition="varchar(32)")
    private String imageId;
    private Type imageType;
    @Lob
    @Column(columnDefinition = "blob")
    private byte[] imageData;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String id) {
        this.imageId = id;
    }

    public Type getImageType() {
        return imageType;
    }

    public void setImageType(Type imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public enum Type {
        PNG, WEBP, JPEG, BMP
    }
}
