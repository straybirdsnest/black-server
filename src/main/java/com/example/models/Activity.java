package com.example.models;

import com.example.models.proxies.ImageProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String location;

    @ManyToOne
    @JoinColumn(name = "promoter_id")
    private User promoter;

    @Column(columnDefinition = "text")
    private String content;

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
    private ImageProxy imageProxy;

    public Activity(){
        imageProxy = new ImageProxy();
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

    public String getCoverImageAccessToken() {
        Image image = getCoverImage();
        logger.debug("image"+image);
        return imageProxy.getAccessToken(image);
    }

    //</editor-fold>

    public enum Type {
        MATCH, BLACK
    }

    public enum Status {
        READY, RUNNING, STOPPED
    }
}
