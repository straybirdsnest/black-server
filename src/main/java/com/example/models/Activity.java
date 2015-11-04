package com.example.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Activity {
    @Id
    @GeneratedValue
    private Integer id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String location;

    @ManyToOne
    @JoinColumn(name = "promoter_id")
    private User promoter;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "enum('match', 'black')")
    private Type type;

    @Column(columnDefinition = "enum('ready', 'running', 'stopped')")
    private Status status;

    @Column(columnDefinition = "text")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getPromoter() {
        return promoter;
    }

    public void setPromoter(User promoter) {
        this.promoter = promoter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public enum Type {
        MATCH, BLACK
    }

    public enum Status {
        READY, RUNNING, STOPPED
    }
}
