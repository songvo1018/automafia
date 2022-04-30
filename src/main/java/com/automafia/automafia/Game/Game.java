package com.automafia.automafia.Game;

import com.automafia.automafia.Game.Config.GameConfig;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "game_config", nullable = false)
    private GameConfig gameConfig;

    private String acceptedRoles;

    private String freeRoles;

    protected Game() {};

    public Game(String creatorName, GameConfig gameConfig) {
        this.creatorName = creatorName;
        this.gameKey = creatorName.hashCode();
        this.finished = false;
        this.gameConfig = gameConfig;
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

    public GameConfig getGameConfig() {
        return gameConfig;
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

    public String getAcceptedRoles() {
        return acceptedRoles;
    }

    public void setAcceptedRoles(String acceptedRoles) {
        this.acceptedRoles = acceptedRoles;
    }

    public String getFreeRoles() {
        return freeRoles;
    }

    public void setFreeRoles(String freeRoles) {
        this.freeRoles = freeRoles;
    }
}

