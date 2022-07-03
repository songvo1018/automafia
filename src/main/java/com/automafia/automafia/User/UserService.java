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
    public User createNewUser(long gameId, String name, List<Roles> freeRolesForGame) {
        User user = new User(gameId, name);
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
        return userRepository.findByGameIdAndAliveStatusOrAliveStatus(game.getId(), AliveStatus.ALIVE,
                AliveStatus.HEALED);
    }

    public boolean existUserMovedStatus(Game game, AliveStatus aliveStatus, MoveStatus status, Roles roleType) {
        return userRepository.existsUserByGameIdAndAliveStatusAndMoveStatusAndRoleTypeNot(game.getId(), aliveStatus, status,
                roleType);
    }
    public User findFirstByGameAndMoveStatusIsAndAliveStatusAndRoleTypeIsNot(
            Game game,
            MoveStatus status,
            AliveStatus aliveStatus,
            Roles roleType) {
        return userRepository.findFirstByGameIdAndMoveStatusIsAndAliveStatusAndRoleTypeIsNot(game.getId(), status, aliveStatus,
                roleType);
    }
    public int getCountConnectedUsersToGame(long gameId) {
        return userRepository.countUserByGameId(gameId);
    }

    public boolean isMafiaWon(Game game) {
        List<User> aliveCitizen = userRepository.findByGameIdAndAliveStatusAndRoleTypeNot(game.getId(), AliveStatus.ALIVE,
                Roles.MAFIA);
        List<User> aliveMafia = userRepository.findByGameIdAndAliveStatusAndRoleType(game.getId(), AliveStatus.ALIVE,
                Roles.MAFIA);
// TODO: (!) MOVE TO CONFIG MINIMAL COUNT NOT MAFIA USERS
        return !aliveMafia.isEmpty() && (aliveCitizen.size() < 2 || aliveCitizen.isEmpty());
    }

    public List<User> getDeadUsers(Game game) {
        return userRepository.findByGameIdAndAliveStatusNot(game.getId(), AliveStatus.ALIVE);
    }

    public int findAliveNightUsers(Game game) {
        return userRepository.findByGameIdAndAliveStatusAndRoleTypeNot(game.getId(), AliveStatus.ALIVE, Roles.CITIZEN).size();
    }
}
