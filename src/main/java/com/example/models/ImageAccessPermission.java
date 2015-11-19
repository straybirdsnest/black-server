package com.example.models;

import javax.persistence.*;

@Entity
public class ImageAccessPermission {
    public static final int FLAG_PRIVATE = 0x80000000;       // 这一位为 0 表示共有
    public static final int FLAG_USER_GROUP = 0x00000001;    // 表示用户组内可见
    public static final int FLAG_USER = 0x00000002;          // 用户可见

    @Id
    @GeneratedValue
    private Long id;

    private Integer flags;

    private Integer uid;

    private Long gid;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    //<editor-fold desc="=== Getters & Setters ===">

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    //</editor-fold>
}
