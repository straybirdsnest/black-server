package com.example.models.core;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by otakuplus on 2015/11/2.
 */
@Entity
@Table(name = "tFriendship")
public class Friendship {
    private User userA;
    private User userB;

    public User getUserA() {
        return userA;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public User getUserB() {
        return userB;
    }

    public void setUserB(User userB) {
        this.userB = userB;
    }
}
