package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.User;
import com.automafia.automafia.User.UserAction;

public class Maniac extends Role implements UserAction {
    private User preyUser;

    public Maniac(User user, Roles roleType) {
        super(user, roleType);
    }

    public User getPreyUser() {
        return preyUser;
    }

    public void setPreyUser(User preyUser) {
        this.preyUser = preyUser;
    }
    @Override
    public User effect(long userId) {
        return null;
    }
}
