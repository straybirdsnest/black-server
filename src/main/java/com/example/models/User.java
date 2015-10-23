package com.example.models;

import com.example.models.games.dota2.SteamAccount;
import com.example.utils.EncodePasswordUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yy on 8/30/15.
 */
@Entity
@Table(name = "tUser")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "account", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", columnDefinition = "int", length = 1)
    private boolean enabled;

    @ManyToMany
    private List<UserGroup> userGroups;

    @OneToMany
    private List<SteamAccount> steamAccounts;

    protected User() {}

    public User(String username, String password){
        this.username = username;
        this.password = EncodePasswordUtil.encode(password);
    }

    @Override
    public String toString() {
        return String.format(
                "User[userId=%d, username='%s', password='%s']",
                userId, username, password);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = EncodePasswordUtil.encode(password);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public List<SteamAccount> getSteamAccounts() {
        return steamAccounts;
    }

    public void setSteamAccounts(List<SteamAccount> steamAccounts) {
        this.steamAccounts = steamAccounts;
    }
}
