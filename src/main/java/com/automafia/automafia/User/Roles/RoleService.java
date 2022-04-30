package com.automafia.automafia.User.Roles;

import com.automafia.automafia.User.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRandomRoleInGameFor(User user, List<Roles> freeRolesForGame) {
        Collections.shuffle(freeRolesForGame, new Random());
        return getRoleFor(user, freeRolesForGame.get(0));
    }

    public Role getRoleFor(User user, Roles roles) {
        switch (roles) {
            case MAFIA:
                return new Mafia(user, roles);
            case DETECTIVE:
                return new Detective(user, roles);
            case DOCTOR:
                return new Doctor(user, roles);
            case MANIAC:
                return new Maniac(user, roles);
            default:
                return new Citizen(user, roles);
        }
    }
}
