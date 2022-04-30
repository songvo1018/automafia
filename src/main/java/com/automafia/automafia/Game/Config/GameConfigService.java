package com.automafia.automafia.Game.Config;

import com.automafia.automafia.Game.GameInfo;
import com.automafia.automafia.User.Roles.Roles;
import com.automafia.automafia.User.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameConfigService {
    private final GameConfigRepository gameConfigRepository;

    public GameConfigService(GameConfigRepository gameConfigRepository) {
        this.gameConfigRepository = gameConfigRepository;
    }

    public void save(GameConfig gameConfig) {
        gameConfigRepository.save(gameConfig);
    }

    public List<Roles> getAcceptedRolesForGame(GameConfig gameConfig) {
        List<Roles> acceptedRole = new ArrayList<>();
        for (int i = 0; i <= gameConfig.getUsersCount() + 1; i++) {
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
                    if (gameConfig.isDoctorExist())
                        acceptedRole.add(Roles.DOCTOR);
                    break;
                case 7:
                    if (gameConfig.isManiacExist())
                        acceptedRole.add(Roles.MANIAC);
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
}
