package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.AliveStatus;
import com.automafia.automafia.User.User;
import com.automafia.automafia.User.Actions.UserAction;
import com.automafia.automafia.User.UserService;

import java.util.List;
import java.util.Optional;

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
    public User effect(long userId, UserService userService) {
        Optional<User> target = userService.findById(userId);
        if (target.isPresent() && target.get().getRole().getRoleType() == Roles.MAFIA) {
            target.get().setAliveStatus(AliveStatus.EXPOSED);
        }
        return target.orElse(null);
    }
}
