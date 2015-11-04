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
    @Column(columnDefinition = "enum('male', 'female', 'secret')")
    private Gender gender;
    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] avatar;
    private LocalDate birthday;

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

    public enum Gender {
        MALE, FEMALE, SECRET
    }
}
