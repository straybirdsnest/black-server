package com.example.models;

import com.example.config.jsonviews.UserView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDate;

@Embeddable
public class Profile {
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private String nickname;
    @Column(name = "realname")
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private String realName;
    @Column(name = "idcard")
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private String idCard;
    @Column(columnDefinition = "enum('MALE', 'FEMAE', 'SECRET')")
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private Gender gender;
    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] avatar;
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private LocalDate birthday;
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private String signature;
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private String hometown;
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private String highschool;
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id",referencedColumnName = "id")
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private College college;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academy_id",referencedColumnName = "id")
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private Academy academy;
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private String grade;

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

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
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

    @JsonView(UserView.ProfileWithoutAvatar.class)
    @JsonProperty("college")
    public String getCollegeName()
    {
        if(college != null){
            return college.getName();
        }else
        {
            return null;
        }
    }

    @JsonView(UserView.ProfileWithoutAvatar.class)
    @JsonProperty("academy")
    public String getAcademyName(){
        if(academy!= null){
            return academy.getName();
        }else {
            return null;
        }
    }

    public enum Gender {
        MALE, FEMALE, SECRET
    }
}
