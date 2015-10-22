package com.example.models.games.dota2;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by yy on 9/11/15.
 */
@Entity
@Table(name = "tDota2Match")
public class Match {
    public static final long CAPTAINS_MODE = 2;
    @Id
    private Long matchId;
    private String season;
    private boolean radiantWin;
    private long duration;
    private Date firstBloodTime;
    private Date startTime;
    private int humanPlayer;

}
