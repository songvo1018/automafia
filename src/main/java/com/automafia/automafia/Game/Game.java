package com.automafia.automafia.Game;

import com.automafia.automafia.Round.Round;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String creatorName;
    private int gameKey;
    private boolean finished;
    private long currentRoundId;

    protected Game() {};

    public Game(String creatorName) {
        this.creatorName = creatorName;
        this.gameKey = creatorName.hashCode();
        this.finished = false;
    }

    @Override
    public String toString() {
        return String.format("Game[id=%d, creatorName='%s', gameKey='%s', finished='%s', roundId='%s']", id,
                creatorName,
                gameKey,
                finished, currentRoundId);
    }

    public long getId() {
        return id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public long getCurrentRoundId() {
        return currentRoundId;
    }

    public void setCurrentRoundId(long currentRoundId) {
        this.currentRoundId = currentRoundId;
    }

    public int getGameKey() {
        return gameKey;
    }

    public Game setFinished(boolean status) {
        System.out.println("before set: " + this.finished);
        this.finished = status;
        System.out.println("after set: " + this.finished);
        return this;
    }
}
