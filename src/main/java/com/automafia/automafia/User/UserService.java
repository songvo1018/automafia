package com.automafia.automafia.User;

import com.automafia.automafia.Game.Game;
import com.automafia.automafia.User.Roles.RoleService;
import com.automafia.automafia.User.Roles.Roles;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    public User createNewUser(Game game, String name, List<Roles> freeRolesForGame) {
        User user = new User(game, name);
        user.setRole(roleService.getRandomRoleInGameFor(user, freeRolesForGame));
        userRepository.save(user);
        return user;
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public List<User> findByGameId(long id) {
        return userRepository.findAllByGameId(id);
    }
}
