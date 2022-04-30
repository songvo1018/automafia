package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.AliveStatus;
import com.automafia.automafia.User.User;
import com.automafia.automafia.User.Actions.UserAction;
import com.automafia.automafia.User.UserService;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Optional;

@Entity
public class Doctor extends Role implements UserAction {
    @ManyToMany
    private List<User> savedUsers;

    public Doctor(User user, Roles roleType) {
        super(user, roleType);
    }

    public Doctor() {}

    public List<User> getSavedUsers() {
        return savedUsers;
    }

    public void setSavedUsers(List<User> savedUsers) {
        this.savedUsers = savedUsers;
    }

    @Override
    public User effect(long userId, UserService userService) {
        Optional<User> target = userService.findById(userId);
        if (target.isPresent() && (target.get().getAliveStatus() == AliveStatus.KILLED_MAFIA || target.get().getAliveStatus() == AliveStatus.KILLED_MAFIA)) {
            target.get().setAliveStatus(AliveStatus.HEALED);
        }
        return target.orElse(null);
    }
}
