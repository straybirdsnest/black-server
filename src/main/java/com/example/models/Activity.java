package com.example.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tActivity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate startTime;
    private LocalDate endTime;
    private String location;
    @ManyToOne
    @JoinColumn(name = "promoter", referencedColumnName = "id")
    private User promoter;
    @Lob
    @Column(columnDefinition = "text")
    private String content;
    @Column(columnDefinition = "enum('match', 'black')")
    private Type type;
    @Column(columnDefinition = "enum('ready', 'running', 'stopped')")
    private Status status;
    @Lob
    @Column(columnDefinition = "text")
    private String remarks;
    @OneToOne
    @JoinColumn(name = "group", referencedColumnName = "id")
    private Group group;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
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
