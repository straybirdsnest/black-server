package com.example.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
public class RegistrationInfo {
    private LocalDateTime regTime;

    private String regIp;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "reg_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "reg_latitude"))
    })
    private Location regLocation;

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Location getRegLocation() {
        return regLocation;
    }

    public void setRegLocation(Location regLocation) {
        this.regLocation = regLocation;
    }
}
