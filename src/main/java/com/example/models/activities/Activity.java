package com.example.models.activities;

import com.example.models.core.User;
import com.example.models.games.GameType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "tActivity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;
    private String activityName;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "game_type_id")
    private GameType gameType;
    private Date startTime;
    private Date endTime;
    private String place;
    private Date deadLine;
    private int sizeofLimit;
    private int visiualbility;
    @OneToMany
    private User sponsor;
    @ManyToMany
    private List<User> attenders;

}
