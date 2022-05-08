package com.automafia.automafia.Game;

import com.automafia.automafia.Round.Round;
import com.automafia.automafia.User.User;

import java.util.List;

public class GameInfo {
//    TODO: Implement additional constuctor
    public List<User> getUsers() {
        return users;
    }

    private Round currentRound;

    public GameInfo init(Game game, List<User> users, Round currentRound) {
        setGame(game);
        setUsers(users);
        setCurrentRound(currentRound);
        return this;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private List<User> users;
    private Game game;

    public GameInfo() {}
//    current time
}
