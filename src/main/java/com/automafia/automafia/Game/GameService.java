package com.automafia.automafia.Game;

import com.automafia.automafia.Game.Config.GameConfig;
import com.automafia.automafia.Game.Config.GameConfigService;
import com.automafia.automafia.Round.Round;
import com.automafia.automafia.Round.RoundService;
import com.automafia.automafia.User.AliveStatus;
import com.automafia.automafia.User.MoveStatus;
import com.automafia.automafia.User.Roles.Roles;
import com.automafia.automafia.User.User;
import com.automafia.automafia.User.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GameService implements IGameService {
    private final GameRepository gameRepository;
    private final RoundService roundService;
    private final UserService userService;
    private final GameConfigService gameConfigService;

    public GameService(GameRepository gameRepository,
                       GameConfigService gameConfigService,
                       RoundService roundService,
                       UserService userService) {
        this.gameRepository = gameRepository;
        this.roundService = roundService;
        this.userService = userService;
        this.gameConfigService = gameConfigService;
    }

    @Override
    public List<Game> getGamesInfo() {
        return gameRepository.findByFinished(false);
    }

    @Override
    public Integer getGameKey(long id) {
        return gameRepository.findById(id).getGameKey();
    }

    @Override
    public Game startGame(String creator, int usersCount, boolean isManiacExist, boolean isDoctorExist, boolean isMafiaUnanimousDecision) {
        GameConfig gameConfig = new GameConfig();
        gameConfig.init(isDoctorExist, isManiacExist, isMafiaUnanimousDecision, usersCount);
        gameConfigService.save(gameConfig);

        Game createdGame = new Game(creator, gameConfig);
        Round currentRound = roundService.createNewRound(0);
        createdGame.setCurrentRoundId(currentRound.getId());

        gameRepository.save(createdGame);
        GameInfo gameInfo = getGameInfo(createdGame.getId());
        User user = userService.createNewUser(createdGame.getId(), creator,
                gameConfigService.getFreeRolesForGame(gameInfo));
        createdGame.setUsersConnectedCount(userService.getCountConnectedUsersToGame(createdGame.getId()));



//        actualize gameInfo
        gameInfo = getGameInfo(createdGame.getId());
        createdGame.setAcceptedRoles(gameConfigService.getAcceptedRolesForGame(gameConfig).toString());
        createdGame.setFreeRoles(gameConfigService.getFreeRolesForGame(gameInfo).toString());
        createdGame.setCountNightUsers(gameConfigService.getCountNightUsers(gameConfig));
        gameRepository.save(createdGame);
        return createdGame;
    }

    @Override
    public Game endGame(long id) {
        Game game = gameRepository.findById(id);
        if (game.isFinished()) return game;
        game.setFinished(true);
        roundService.endLastRound(game.getCurrentRoundId());
        gameRepository.save(game);
        return game;
    }

    @Override
    public GameInfo getGameInfo(long id) {
        Game game = gameRepository.findById(id);
        Optional<Round> currentRound = roundService.getRoundById(game.getCurrentRoundId());
        List<User> usersInGame = userService.findByGameId(id);
        GameInfo gameInfo = new GameInfo();
        gameInfo.init(game, usersInGame, currentRound.get());
        return gameInfo;
    }

    @Override
    public Game nextRound(long id) {
        Game game = gameRepository.findById(id);
        if (game.isFinished()) return game;
        Round lastRound = roundService.endLastRound(game.getCurrentRoundId());
        Round newRound = roundService.createNewRound(lastRound.getRoundNumber());

        List<User> alive = userService.setMoveStatusToAliveUsers(game);
        List<User> deadUsers = userService.getDeadUsers(game);
        for (User user : deadUsers) {
            user.setMoveStatus(MoveStatus.CANT_MOVE);
        }

        game.setCountNightUsers(userService.findAliveNightUsers(game));
        game.setFinished(userService.isMafiaWon(game));

        game.setCurrentRoundId(newRound.getId());
        game.setRoundNumber(newRound.getRoundNumber());
        gameRepository.save(game);
        return game;
    }

    public boolean selectTargetUser(long gameId, long userId, long targetId) {
        Game game = gameRepository.findById(gameId);
        Optional<Round> currentRound = roundService.getRoundById(game.getCurrentRoundId());
        if (currentRound.isPresent()) {
            User user = userService.findById(userId).get();
            User target = userService.findById(targetId).get();
            if(user.getMoveStatus() == MoveStatus.MOVED) {
                return false;
            }
            currentRound.get().setTarget(userId, targetId);
            roundService.save(currentRound.get());
            return true;
        } else {
            throw new IllegalArgumentException("Round by id=" + game.getCurrentRoundId() + " not found");
        }

    }

    public User nextUserTurnToGo(long gameId) throws IllegalStateException, IllegalArgumentException {
        Game game = gameRepository.findById(gameId);
        Optional<Round> currentRound = roundService.getRoundById(game.getCurrentRoundId());
        if (currentRound.isPresent()) {
            if (!userService.existUserMovedStatus(game, AliveStatus.ALIVE, MoveStatus.READY_MOVE, Roles.CITIZEN)) {
                throw new IllegalStateException("All users be moved in this round for game id=" + gameId);
            }
            User userToGo = userService.findFirstByGameAndMoveStatusIsAndAliveStatusAndRoleTypeIsNot(
                    game,
                    MoveStatus.READY_MOVE,
                    AliveStatus.ALIVE,
                    Roles.CITIZEN);

            Map<Long, Long> usersAndTargets = currentRound.get().getUserAndTarget();
            if (usersAndTargets == null || usersAndTargets.isEmpty() || !usersAndTargets.containsKey(userToGo.getId())) {
                return userToGo;
            }
            long targetId = usersAndTargets.get(userToGo.getId());

//            TODO: CHECK MANIAC BEFORE EFFECT
            userToGo.getRole().effect(targetId, userService);
            userToGo.setMoveStatus(MoveStatus.MOVED);
            userService.save(userToGo);
            return userToGo;
        } else {
            throw new IllegalArgumentException("Round by id=" + game.getCurrentRoundId() + " not found");
        }
    }

    @Override
    public Game connectTo(long gameId, String username) {
        Game game = gameRepository.findById(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game with id=" + gameId + " not found");
        } else if (game.isFinished()) {
            throw new IllegalStateException("Game id=" + gameId + " already finished");
        }
        GameInfo gameInfo = getGameInfo(gameId);
        List<Roles> freeRolesForGame = gameConfigService.getFreeRolesForGame(gameInfo);
        if (freeRolesForGame.isEmpty()) {
            throw new IllegalStateException("Can`t get free role to this game. Users connected: " + game.getCountConnectedUsers());
        }
        User user = userService.createNewUser(game.getId(), username, freeRolesForGame);

//        todo: need create update method
        gameInfo = getGameInfo(gameId);
        game.setUsersConnectedCount(userService.getCountConnectedUsersToGame(game.getId()));
        game.setFreeRoles(gameConfigService.getFreeRolesForGame(gameInfo).toString());
        gameRepository.save(game);
        return game;
    }
}
