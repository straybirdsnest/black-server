package com.example.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Activity {

    @Transient
    private final static Logger logger = LoggerFactory.getLogger(Activity.class);

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cover_image_id")
    private Image coverImage;

    private Date startTime;

    private Date endTime;

    private String location;

    @ManyToOne
    @JoinColumn(name = "promoter_id")
    private User promoter;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(columnDefinition = "enum('MATCH', 'BLACK')")
    private Type type;

    @Column(columnDefinition = "enum('READY', 'RUNNING', 'STOPPED')")
    private Status status;

    @Column(columnDefinition = "text")
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Transient
    private String coverImageAccessToken;

    public Activity() {

    }

    //<editor-fold desc="=== Getters & Setters ===">

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Image getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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

    public String getCoverImageAccessToken() {
        return coverImageAccessToken;
    }

    public void setCoverImageAccessToken(String accessToken) {
        this.coverImageAccessToken = accessToken;
    }

    public String getGameName() {
        if (game != null){
            return game.getName();
        }
        return null;
    }

//</editor-fold>

    public enum Type {
        MATCH, BLACK
    }

    public enum Status {
        READY, RUNNING, STOPPED
    }
}
