package com.automafia.automafia.Round;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int roundNumber;

    private boolean roundFinished;

    protected Round() {}

    @Override
    public String toString() {
        return String.format("Game[id=%d, roundNumber='%s', roundFinished='%s']", id, roundNumber, roundFinished );
    }
    public Round(int roundNumber) {
        System.out.println("CREATED ROUND SASA?");
        this.roundNumber = roundNumber;
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
