package com.example.models.core;

import com.example.models.profiles.Academy;
import com.example.models.profiles.College;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.time.Instant;

/**
 * Created by yy on 8/30/15.
 */
@Entity
@Table(name = "tUser")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String phone;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private String nickname;

    @Column(name = "realname")
    private String realName;

    @Column(name = "idcard")
    private String idCard;

    private boolean enabled;

    private Gender gender;

    @OneToOne
    private College college;

    @OneToOne
    private Academy academy;

    private Blob avatar;

    private Instant birthday;

    private Instant regTime;

    private String regIp;

    private String regGps;

    protected User() {}

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, nickname='%s', password='%s']",
                id, nickname, password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public Instant getRegTime() {
        return regTime;
    }

    public void setRegTime(Instant regTime) {
        this.regTime = regTime;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getRegGps() {
        return regGps;
    }

    public void setRegGps(String regGps) {
        this.regGps = regGps;
    }

    public enum Gender
    {
        MALE,FEMALE,SECRET
    }

}
