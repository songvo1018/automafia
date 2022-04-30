package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.User;
import com.automafia.automafia.User.UserAction;

public class Mafia extends Role implements UserAction {
//    private int order;

    public Mafia(User user, Roles roleType) {
        super(user, roleType);
//        this.order = order;
    }

    @Override
    public User effect(long userId) {
//        if all mafia-users chose one user - kill him
        return null;
    }
}
