package com.automafia.automafia.Game;

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
    private int roundNumber;

    protected Game() {};

    public Game(String creatorName) {
        this.creatorName = creatorName;
        this.gameKey = creatorName.hashCode();
        this.finished = false;
    }

    @Override
    public String toString() {
        return "Game {" + id + ", " +
                "creatorName: " + creatorName + ", " +
                "gameKey: " + gameKey + ", " +
                "finished: " + finished + ", " +
                "roundId: " + currentRoundId + ", " +
                "roundNumber: " + roundNumber + ", " +
                "}";
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
    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setCurrentRoundId(long currentRoundId) {
        this.currentRoundId = currentRoundId;
    }

    public int getGameKey() {
        return gameKey;
    }

    public void setFinished(boolean status) {
        this.finished = status;
    }

    public boolean isFinished() {
        return finished;
    }
}
