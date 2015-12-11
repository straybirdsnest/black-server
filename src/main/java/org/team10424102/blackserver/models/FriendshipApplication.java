package org.team10424102.blackserver.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_friendship_application")
public class FriendshipApplication {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User applicant;

    @ManyToOne
    private User target;

    private String attachment;

    private Date creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
