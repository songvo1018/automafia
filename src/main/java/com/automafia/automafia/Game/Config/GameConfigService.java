package com.automafia.automafia.Game.Config;

import com.automafia.automafia.Game.GameInfo;
import com.automafia.automafia.User.Roles.Roles;
import com.automafia.automafia.User.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameConfigService {
    private final GameConfigRepository gameConfigRepository;

    public GameConfigService(GameConfigRepository gameConfigRepository) {
        this.gameConfigRepository = gameConfigRepository;
    }

    public List<GameConfig> findAllConfig() {
        return (List<GameConfig>) this.gameConfigRepository.findAll();
    }

    public boolean isGameConfigsEmpty() {
        return gameConfigRepository.count() == 0;
    }

    public void save(GameConfig gameConfig) {
        gameConfigRepository.save(gameConfig);
    }

    public List<Roles> getAcceptedRolesForGame(GameConfig gameConfig) {
        List<Roles> acceptedRole = new ArrayList<>();
        for (int i = 1; i < gameConfig.getUsersCount() + 1; i++) {
            switch (i) {
                case 1:
                case 2:
                case 9:
                    acceptedRole.add(Roles.MAFIA);
                    break;
                case 3:
                case 4:
                case 8:
                case 11:
                    acceptedRole.add(Roles.CITIZEN);
                    break;
                case 5:
                case 10:
                    acceptedRole.add(Roles.DETECTIVE);
                    break;
                case 6:
                    if (gameConfig.isDoctorExist()) {
                        acceptedRole.add(Roles.DOCTOR);
                    } else {
                        acceptedRole.add(Roles.MAFIA);
                    }
                    break;
                case 7:
                    if (gameConfig.isManiacExist()) {
                        acceptedRole.add(Roles.MANIAC);
                    } else {
                        acceptedRole.add(Roles.CITIZEN);
                    }
                    break;
            }
        }
        return acceptedRole;
    }

    public List<Roles> getFreeRolesForGame(GameInfo gameInfo) {
        List<Roles> acceptedRole = getAcceptedRolesForGame(gameInfo.getGame().getGameConfig());
        List<User> usersInGame = gameInfo.getUsers();
        List<Roles> notUsedRoles = new ArrayList<>();
        notUsedRoles.addAll(acceptedRole);

        for (User user: usersInGame) {
            Roles busyRole = user.getRole().getRoleType();
            if (acceptedRole.contains(busyRole)) {
                notUsedRoles.remove(busyRole);
            }
        }
        return notUsedRoles;
    }

    public int getCountNightUsers(GameConfig gameConfig) {
        List<Roles> acceptedRole = getAcceptedRolesForGame(gameConfig);
        int count = 0;
        for (Roles role : acceptedRole) {
            if (role.equals(Roles.CITIZEN)) continue;
            count++;
        }
        return count;
    }

    public Optional<GameConfig> findById(Long gameConfigId) {
        return gameConfigRepository.findById(gameConfigId);
    }
}
