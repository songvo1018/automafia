package com.automafia.automafia.Game;

import com.automafia.automafia.Game.Config.GameConfig;
import com.automafia.automafia.Game.Config.GameConfigService;
import com.automafia.automafia.Round.Round;
import com.automafia.automafia.Round.RoundService;
import com.automafia.automafia.User.AliveStatus;
import com.automafia.automafia.User.MoveStatus;
import com.automafia.automafia.User.User;
import com.automafia.automafia.User.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
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
        gameRepository.save(createdGame);
        GameInfo gameInfo = getGameInfo(createdGame.getId());

        Round currentRound = roundService.createNewRound(0);
        createdGame.setCurrentRoundId(currentRound.getId());
        createdGame.setAcceptedRoles(gameConfigService.getFreeRolesForGame(gameInfo).toString());
        createdGame.setFreeRoles(gameConfigService.getFreeRolesForGame(gameInfo).toString());
        gameRepository.save(createdGame);
        User user = userService.createNewUser(createdGame, creator, gameConfigService.getFreeRolesForGame(gameInfo));
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
        List<User> usersInGame = userService.findByGameId(id);
        GameInfo gameInfo = new GameInfo();
        gameInfo.setGame(game);
        gameInfo.setUsers(usersInGame);
        return gameInfo;
    }

    @Override
    public Game nextRound(long id) {
        Game game = gameRepository.findById(id);
        if (game.isFinished()) return game;
        Round lastRound = roundService.endLastRound(game.getCurrentRoundId());
        Round newRound = roundService.createNewRound(lastRound.getRoundNumber());

        List<User> alive = userService.setMoveStatusToAliveUsers(game, MoveStatus.READY_MOVE);

        game.setCurrentRoundId(newRound.getId());
        game.setRoundNumber(newRound.getRoundNumber());
        gameRepository.save(game);
        return game;
    }

    public User nextToGo(long gameId) {
        Game game = gameRepository.findById(gameId);
        Optional<Round> currentRound = roundService.getRoundById(game.getCurrentRoundId());
        if (currentRound.isPresent()) {
            if (!userService.existUserMovedStatus(game, AliveStatus.ALIVE, MoveStatus.READY_MOVE)) {
                throw new IllegalStateException("All users be moved in this round for game id=" + gameId);
            }
            User userToGo = userService.findFirstByGameIdAndAliveStatusAndMovedStatus(
                    game,
                    MoveStatus.READY_MOVE,
                    AliveStatus.ALIVE);
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
        }
        GameInfo gameInfo = getGameInfo(gameId);
        User user = userService.createNewUser(game, username, gameConfigService.getFreeRolesForGame(gameInfo));

        game.setAcceptedRoles(gameConfigService.getFreeRolesForGame(gameInfo).toString());
        game.setFreeRoles(gameConfigService.getFreeRolesForGame(gameInfo).toString());
        gameRepository.save(game);
        return game;
    }
}
