package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.User;
import com.automafia.automafia.User.UserAction;

import java.util.List;

public class Doctor extends Role implements UserAction {
    private List<User> savedUsers;

    public Doctor(User user, Roles roleType) {
        super(user, roleType);
    }

    public List<User> getSavedUsers() {
        return savedUsers;
    }

    public void setSavedUsers(List<User> savedUsers) {
        this.savedUsers = savedUsers;
    }

    @Override
    public User effect(long userId) {
        return null;
    }
}
