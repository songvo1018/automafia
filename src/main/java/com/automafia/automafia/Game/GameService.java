package com.automafia.automafia.Game;

import com.automafia.automafia.Round.Round;
import com.automafia.automafia.Round.RoundService;
import com.automafia.automafia.User.Roles.Roles;
import com.automafia.automafia.User.User;
import com.automafia.automafia.User.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService implements IGameService {
    private final GameRepository gameRepository;
    private final RoundService roundService;
    private final UserService userService;

    public GameService(GameRepository gameRepository, RoundService roundService, UserService userService) {
        this.gameRepository = gameRepository;
        this.roundService = roundService;
        this.userService = userService;
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
    public Game startGame(String creator) {
        Game createdGame = new Game(creator);
        User user = userService.createNewUser(createdGame, creator, getFreeRolesForGame(createdGame.getId()));
        Round currentRound = roundService.createNewRound(0);
        createdGame.setCurrentRoundId(currentRound.getId());
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
        List<User> usersInGame = userService.findByGameId(id);
        GameInfo gameInfo = new GameInfo();
        gameInfo.setGame(game);
        System.out.println(gameInfo.toString());
        gameInfo.setUsers(usersInGame);
        System.out.println(gameInfo.toString());
        return gameInfo;
    }

    @Override
    public Game nextRound(long id) {
        Game game = gameRepository.findById(id);
        if (game.isFinished()) return game;
        Round lastRound = roundService.endLastRound(game.getCurrentRoundId());
        Round newRound = roundService.createNewRound(lastRound.getRoundNumber());
        game.setCurrentRoundId(newRound.getId());
        game.setRoundNumber(newRound.getRoundNumber());
        gameRepository.save(game);
        return game;
    }

    @Override
    public Game connectTo(long gameId, String username) {
        Game game = gameRepository.findById(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game with id= " + gameId + " not found");
        }
        User user = userService.createNewUser(game, username, getFreeRolesForGame(gameId));
        return game;
    }

    public List<Roles> getFreeRolesForGame(long id) {
        GameInfo gameInfo = getGameInfo(id);
//        TODO: REPLACE TO CONFIGURATION GAME IN CREATION TIME
        List<Roles> acceptedRole = new ArrayList<>();
        acceptedRole.add(Roles.MAFIA);
        acceptedRole.add(Roles.MAFIA);
        acceptedRole.add(Roles.CITIZEN);
        acceptedRole.add(Roles.CITIZEN);
        acceptedRole.add(Roles.CITIZEN);
        acceptedRole.add(Roles.MANIAC);
        acceptedRole.add(Roles.DETECTIVE);
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
