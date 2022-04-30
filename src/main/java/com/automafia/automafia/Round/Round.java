package com.automafia.automafia.Round;

import com.automafia.automafia.Round.UserList.UserList;
import com.automafia.automafia.User.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int roundNumber;

//    @OneToOne(cascade = CascadeType.MERGE, fetch=FetchType.EAGER)
//    @PrimaryKeyJoinColumn
//    private UserList userList;
//
//    public UserList getUserList() {
//        return userList;
//    }
//
//    public void setUserList(UserList userList) {
//        this.userList = userList;
//    }

//    public List<User> getUserList() {
//        return userList;
//    }
//
//    public void setUserList(List<User> userList) {
//        this.userList = userList;
//    }

//    @OneToMany(targetEntity = User.class, mappedBy = "user", fetch = FetchType.EAGER)
//    private List<User> userList;

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
