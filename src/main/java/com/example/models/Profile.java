package com.example.models;

import javax.persistence.*;
import java.time.LocalDate;

@Embeddable
public class Profile {
    private String nickname;

    @Column(name = "realname")
    private String realName;

    @Column(name = "idcard")
    private String idCard;

    @Column(columnDefinition = "enum('MALE', 'FEMAE', 'SECRET')")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private Image avatar;

    private LocalDate birthday;

    private String signature;

    private String hometown;

    private String highschool;

    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id", referencedColumnName = "id")

    private College college;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academy_id", referencedColumnName = "id")

    private Academy academy;

    private String grade;

    @ManyToOne
    @JoinColumn(name = "background_image_id")
    private Image backgroundImage;

    //<editor-fold desc="=== Getters & Setters ===">

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getHighschool() {
        return highschool;
    }

    public void setHighschool(String highschool) {
        this.highschool = highschool;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    //</editor-fold>

    public String getCollegeName() {
        if (college != null) {
            return college.getName();
        } else {
            return null;
        }
    }

    public String getAcademyName() {
        if (academy != null) {
            return academy.getName();
        } else {
            return null;
        }
    }

    public enum Gender {
        MALE, FEMALE, SECRET
    }
}
