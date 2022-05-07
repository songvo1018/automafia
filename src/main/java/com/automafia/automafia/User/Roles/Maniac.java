package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.AliveStatus;
import com.automafia.automafia.User.User;
import com.automafia.automafia.User.Actions.UserAction;
import com.automafia.automafia.User.UserService;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Optional;

@Entity
public class Maniac extends Role {
    @ManyToOne
    private User preyUser = null;

    public Maniac(User user, Roles roleType) {
        super(user, roleType);
    }

    public Maniac() {}

    public User getPreyUser() {
        return preyUser;
    }

    public void setPreyUser(User preyUser) {
        this.preyUser = preyUser;
    }

    public User effect(long targetId, UserService userService) {
        
        Optional<User> target = userService.findById(targetId);
        if (target.isPresent() && getPreyUser() == null) {
            target.get().setAliveStatus(AliveStatus.KILLED_MANIAC);
            userService.save(target.get());
            this.setPreyUser(target.get());
        }
        return target.orElse(null);
    }
}
