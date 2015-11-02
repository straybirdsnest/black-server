package com.example.models.core;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tSubscription")
public class Subscription {

    private User broadcaster;
    private User subscriber;

    public User getBroadcaster() {
        return broadcaster;
    }

    public void setBroadcaster(User broadcaster) {
        this.broadcaster = broadcaster;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }
}
