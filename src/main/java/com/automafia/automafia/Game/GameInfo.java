package com.automafia.automafia.Game;

import com.automafia.automafia.User.User;

import java.util.List;

public class GameInfo {
//    TODO: Implement additional constuctor
    public List<User> getUsers() {
        return users;
    }

    public GameInfo init(Game game, List<User> users) {
        setGame(game);
        setUsers(users);
        return this;
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
