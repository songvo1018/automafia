package com.automafia.automafia.Game;

import com.automafia.automafia.Game.Config.GameConfig;

import javax.persistence.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * Value to view name of game creator
     */
    private String creatorName;

    /**
     * Value not used
     */
    private int gameKey;

    /**
     * State of finished game
     */
    private boolean finished;

    /**
     * Unique ID current Round Entity
     */
    private long currentRoundId;

    /**
     * Current round in the game
     */
    private int roundNumber;

    /**
     * Game configuration relation
     */
    @ManyToOne
    @JoinColumn(name = "game_config", nullable = false)
    private GameConfig gameConfig;

//    TODO: THINK ABOUT STORE USERS STATUS IN DB

    /**
     * Value to view accepted roles
     */
    private String acceptedRoles;

    /**
     * Value to view free roles
     */
    private String freeRoles;

    /**
     * Count connected users to game
     */
    private int countConnectedUsers;

    protected Game() {};

    public Game(String creatorName, GameConfig gameConfig) {
        this.creatorName = creatorName;
        this.countConnectedUsers = 1;
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

    public int getCountConnectedUsers() {
        return countConnectedUsers;
    }

    public void setUsersConnectedCount(int countConnectedUsersToGame) {
        this.countConnectedUsers = countConnectedUsersToGame;
    }
}

