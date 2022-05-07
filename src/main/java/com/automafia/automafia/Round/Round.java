package com.automafia.automafia.Round;

import com.automafia.automafia.User.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ElementCollection
    @MapKeyColumn(name="userId")
    @Column(name ="targetId")
    @CollectionTable(name="round_userAndTarget", joinColumns=@JoinColumn(name="round_id"))
    private Map<Long, Long> userAndTarget;

    /**
     * Current round number in the linked game
     */
    private int roundNumber;

//    TODO: MAYBE WE NEED STATE OF DAY like isNight for optimize scheduled task

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

    public Map<Long, Long> setTarget(long userId, long targetId) {
        Map<Long, Long> map = getUserAndTarget();
        map.put(userId, targetId);
        this.userAndTarget = map;
        return map;
    }

    public Map<Long, Long> getUserAndTarget() {
        return userAndTarget;
    }
}
