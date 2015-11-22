package com.example.models;

import com.example.config.converters.json.UserDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 正常的用户从 UID_BASE 开始编号
 */
@Entity
@JsonDeserialize(using = UserDeserializer.class)
public class User {
    public static final int UID_SYSTEM = 0;
    public static final int UID_PUBLIC = 1;
    public static final int UID_BASE = 100;

    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private String email;

    private Boolean enabled;

    @Embedded
    @Basic(fetch = FetchType.LAZY)
    private Profile profile = new Profile();

    @Embedded
    @Basic(fetch = FetchType.LAZY)
    private RegInfo regInfo = new RegInfo();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "T_SUBSCRIPTION",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "broadcaster_id")
    )
    private Set<User> focuses = new HashSet<>();

    @ManyToMany(mappedBy = "focuses", fetch = FetchType.LAZY)
    private Set<User> fans = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Friendship> friendshipSet = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Membership> membershipSet = new HashSet<>();

    //<editor-fold desc="=== Getters & Setters ===">

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public RegInfo getRegInfo() {
        return regInfo;
    }

    public void setRegInfo(RegInfo regInfo) {
        this.regInfo = regInfo;
    }

    public Set<User> getFocuses() {
        return focuses;
    }

    public void setFocuses(Set<User> focuses) {
        this.focuses = focuses;
    }

    public Set<User> getFans() {
        return fans;
    }

    public void setFans(Set<User> fans) {
        this.fans = fans;
    }

    public Set<Friendship> getFriendshipSet() {
        return friendshipSet;
    }

    public void setFriendshipSet(Set<Friendship> friendshipSet) {
        this.friendshipSet = friendshipSet;
    }

    public Set<Membership> getMembershipSet() {
        return membershipSet;
    }

    public void setMembershipSet(Set<Membership> membershipSet) {
        this.membershipSet = membershipSet;
    }

    //</editor-fold>

}
