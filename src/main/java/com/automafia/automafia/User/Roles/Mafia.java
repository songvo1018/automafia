package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.AliveStatus;
import com.automafia.automafia.User.User;
import com.automafia.automafia.User.Actions.UserAction;
import com.automafia.automafia.User.UserService;

import javax.persistence.Entity;
import java.util.Optional;

@Entity
public class Mafia extends Role {

    public Mafia(User user, Roles roleType) {
        super(user, roleType);
    }

    public Mafia() {}

    public User effect(long targetId, UserService userService) {
        Optional<User> target = userService.findById(targetId);
//        TODO: THINK ABOUT Unanimously mafioso
        if (target.isPresent()) {
            target.get().setAliveStatus(AliveStatus.KILLED_MAFIA);
            userService.save(target.get());
        }
        return target.orElse(null);
    }
}
