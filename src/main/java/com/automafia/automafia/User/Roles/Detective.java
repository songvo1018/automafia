package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.User;
import com.automafia.automafia.User.UserAction;

import java.util.List;

public class Detective extends Role implements UserAction {
    private List<User> detectedUsers;

    public List<User> getDetectedUsers() {
        return detectedUsers;
    }

    public void setDetectedUsers(List<User> detectedUsers) {
        this.detectedUsers = detectedUsers;
    }

    public Detective(User user, Roles roleType) {
        super(user, roleType);
    }

    @Override
    public User effect(long userId) {
//        check user
        return null;
    }
}
