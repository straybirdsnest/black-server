package com.example.models;

import com.example.config.jsonviews.UserView;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Embedded
    RegistrationInfo regInfo;
    @Id
    @GeneratedValue
    private Integer id;
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private String phone;
    @JsonView(UserView.ProfileWithoutAvatar.class)
    private String email;
    private boolean enabled;
    @Embedded
    @JsonView(UserView.ProfileWithoutAvatar.class)
    @JsonUnwrapped
    private Profile profile;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_FRIENDSHIP",
            joinColumns = @JoinColumn(name = "user_a"),
            inverseJoinColumns = @JoinColumn(name = "user_b"))
    private Set<User> friends = new HashSet<>();

    public User() {
    }

    public User(String phone) {
        this.phone = phone;
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

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
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
}
