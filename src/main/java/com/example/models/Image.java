package com.example.models;

import com.example.config.converters.json.ImageSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@JsonSerialize(using = ImageSerializer.class)
@Entity
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] data;

    private String hash; // 选择速度最快的信息摘要算法 md5

    private Integer used;

    private String tags;

    //<editor-fold desc="=== Getters & Setters ===">

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    //</editor-fold>
}
