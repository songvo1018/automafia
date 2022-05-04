package com.automafia.automafia.Round;

import com.automafia.automafia.User.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Current round number in the linked game
     */
    private int roundNumber;

    /**
     * State of round
     */
    private boolean roundFinished;

    protected Round() {}

    @Override
    public String toString() {
        return String.format("Game[id=%d, roundNumber='%s', roundFinished='%s']", id, roundNumber, roundFinished );
    }
    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
//        this.userList = new UserList(new ArrayList<User>());
    }

    public boolean isRoundFinished() {
        return roundFinished;
    }

    public void setRoundFinished(boolean roundFinished) {
        this.roundFinished = roundFinished;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public long getId() {
        return id;
    }

}
