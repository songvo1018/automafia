package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.AliveStatus;
import com.automafia.automafia.User.User;
import com.automafia.automafia.User.Actions.UserAction;
import com.automafia.automafia.User.UserService;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Doctor extends Role {
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

    public User effect(long targetId, UserService userService) {
        Optional<User> target = userService.findById(targetId);
        if (target.isPresent() && (target.get().getAliveStatus() == AliveStatus.KILLED_MAFIA || target.get().getAliveStatus() == AliveStatus.KILLED_MAFIA)) {
            target.get().setAliveStatus(AliveStatus.HEALED);
            userService.save(target.get());
            List<User> updatedSavedUsers = new ArrayList<>();
            updatedSavedUsers.add(target.get());
            setSavedUsers(updatedSavedUsers);
        }
        return target.orElse(null);
    }
}
