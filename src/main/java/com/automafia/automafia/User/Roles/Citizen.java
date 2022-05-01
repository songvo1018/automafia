package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.User;
import com.automafia.automafia.User.UserService;

import javax.persistence.Entity;

@Entity
public class Citizen extends Role {

    public Citizen(User user, Roles roleType) {
        super(user, roleType);
    }

    public Citizen() {}

    public User effect(long userId, UserService userService) {
        return super.effect(userId, userService);
    }
}
