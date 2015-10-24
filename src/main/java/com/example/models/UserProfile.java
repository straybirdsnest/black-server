package com.example.models;

import javax.persistence.*;

/**
 * Created by yy on 9/11/15.
 */
@Entity
@Table(name = "tUserProfile")
public class UserProfile extends User {

    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private College college;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Academy academy;

    @OneToOne
    @JoinColumn(name = "avatar")
    private Image avatar;

    @Column(name = "attention")
    private int followingNum;

    @Column(name = "fans")
    private int fansNum;

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

    public Academy getAcademy() {
        return academy;
    }

    public void setAcademy(Academy academy) {
        this.academy = academy;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public int getFollowingNum() {
        return followingNum;
    }

    public void setFollowingNum(int followingNum) {
        this.followingNum = followingNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }
}
