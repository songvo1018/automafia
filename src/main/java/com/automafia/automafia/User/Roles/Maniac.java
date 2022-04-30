package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.AliveStatus;
import com.automafia.automafia.User.User;
import com.automafia.automafia.User.Actions.UserAction;
import com.automafia.automafia.User.UserService;

import java.util.Optional;

public class Maniac extends Role implements UserAction {
    private User preyUser = null;

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
    public User effect(long userId, UserService userService) {
        Optional<User> target = userService.findById(userId);
        if (target.isPresent() && getPreyUser() == null) {
            target.get().setAliveStatus(AliveStatus.KILLED_MANIAC);
            this.setPreyUser(target.get());
        }
        return target.orElse(null);
    }
}
