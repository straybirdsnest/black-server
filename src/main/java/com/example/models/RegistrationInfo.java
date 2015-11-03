package com.example.models;

import javax.persistence.*;
import java.net.InetAddress;
import java.time.LocalDateTime;

@Embeddable
public class RegistrationInfo {
    private LocalDateTime regTime;

    private InetAddress regIp;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "reg_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "reg_latitude"))
    })
    private Location regLocation;
}
