package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.User;

import javax.persistence.Entity;

@Entity
public class Citizen extends Role {

    public Citizen(User user, Roles roleType) {
        super(user, roleType);
    }

    public Citizen() {}
}
