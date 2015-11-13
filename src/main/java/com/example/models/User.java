package com.example.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonDeserialize(using = com.example.config.converters.json.UserDeserilizer.class)
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String phone;

    private String email;

    private boolean enabled;

    @Embedded
    private Profile profile;

    @Embedded
    private RegistrationInfo regInfo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_FRIENDSHIP",
            joinColumns = @JoinColumn(name = "user_a"),
            inverseJoinColumns = @JoinColumn(name = "user_b"))
    private Set<User> following = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "following")
    private Set<User> followed = new HashSet<>();

    public User() {
    }

    public User(String phone) {
        this.phone = phone;
    }

    //<editor-fold desc="=== Getters & Setters ===">

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFollowed() {
        return followed;
    }

    public void setFollowed(Set<User> followed) {
        this.followed = followed;
    }

    public RegistrationInfo getRegInfo() {
        return regInfo;
    }

    public void setRegInfo(RegistrationInfo regInfo) {
        this.regInfo = regInfo;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    //</editor-fold>

}
