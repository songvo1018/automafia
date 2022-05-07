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

    public User save(User user) {
        userRepository.save(user);
        return user;
    }
    public User createNewUser(Game game, String name, List<Roles> freeRolesForGame) {
        User user = new User(game, name);
        user.setRole(roleService.getRandomRoleInGameFor(user, freeRolesForGame));
        user.setAliveStatus(AliveStatus.ALIVE);
        user.setMoveStatus(MoveStatus.READY_MOVE);
        userRepository.save(user);
        return user;
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public List<User> findByGameId(long id) {
        return userRepository.findAllByGameId(id);
    }

    public List<User> findAllByGameId(long id) {
        return userRepository.findAllByGameId(id);
    }

    public List<User> setMoveStatusToAliveUsers(Game game) {
        List<User> alive = findAliveOrHealedByGame(game);
        for (User user : alive) {
            if (user.getAliveStatus() == AliveStatus.HEALED) user.setAliveStatus(AliveStatus.ALIVE);
            user.setMoveStatus(MoveStatus.READY_MOVE);
            save(user);
        }
        return alive;
    }
    public List<User> findAliveOrHealedByGame(Game game) {
        return userRepository.findByGameAndAliveStatusOrAliveStatus(game, AliveStatus.ALIVE, AliveStatus.HEALED);
    }

    public boolean existUserMovedStatus(Game game, AliveStatus aliveStatus, MoveStatus status, Roles roleType) {
        return userRepository.existsUserByGameAndAliveStatusAndMoveStatusAndRoleTypeNot(game, aliveStatus, status,
                roleType);
    }
    public User findFirstByGameAndMoveStatusIsAndAliveStatusAndRoleTypeIsNot(
            Game game,
            MoveStatus status,
            AliveStatus aliveStatus,
            Roles roleType) {
        return userRepository.findFirstByGameAndMoveStatusIsAndAliveStatusAndRoleTypeIsNot(game, status, aliveStatus,
                roleType);
    }
    public int getCountConnectedUsersToGame(Game game) {
        return userRepository.countUserByGame(game);
    }
}
