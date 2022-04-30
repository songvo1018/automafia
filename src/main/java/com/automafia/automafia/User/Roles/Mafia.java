package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.AliveStatus;
import com.automafia.automafia.User.User;
import com.automafia.automafia.User.Actions.UserAction;
import com.automafia.automafia.User.UserService;

import java.util.Optional;

public class Mafia extends Role implements UserAction {

    public Mafia(User user, Roles roleType) {
        super(user, roleType);
    }

    @Override
    public User effect(long userId, UserService userService) {
        Optional<User> target = userService.findById(userId);
//        TODO: THINK ABOUT Unanimously mafioso
        target.ifPresent(user -> user.setAliveStatus(AliveStatus.KILLED_MAFIA));
        return target.orElse(null);
    }
}
