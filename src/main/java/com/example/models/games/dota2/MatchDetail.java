package com.example.models.games.dota2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
@Table(name = "tDota2MatchDetails")
public class MatchDetail {
    @Id
    private Long matchId;
    private Long matchSeqNum;
    private Long accountId;
    private Long player;
    private Hero hero;
    private Item item0;
    private Item item1;
    private Item item2;
    private Item item3;
    private Item item4;
    private Item item5;
}
