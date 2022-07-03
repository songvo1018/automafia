package com.automafia.automafia.Game;

import com.automafia.automafia.Game.Config.GameConfig;
import com.automafia.automafia.Round.Round;
import com.automafia.automafia.User.MoveStatus;
import com.automafia.automafia.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

//TODO: check naming
@Component
public class GameTasks {

    private List<Long> cachedGames = new ArrayList<>();

    private Map<Long, List<Long>> usersMovedByGame = new HashMap<>();

    public static final Logger log = LoggerFactory.getLogger(GameTasks.class);

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private GameService gameService;
//    TODO: create method to check and update round: if users in game = users count from config - game started

    public GameTasks(GameService gameService) {
        this.gameService = gameService;
    }
//    @Scheduled(fixedRate=10000)

    public void reportCurrentTime(){
        log.info("The time is now {}", dateFormat.format(new Date()));
        log.info("Games Info: " + gameService.getGamesInfo().toString());
    }

//    Every 10sec
    @Scheduled(fixedRate=10000)
    public void printFilledLobby() {
        List<Game> games = gameService.getGamesInfo();
        if (getCachedGames().equals(games)) return;
        for (Game game : games) {
            if (getCachedGames().contains(game.getId())) continue;
            GameConfig gameConfig = game.getGameConfig();
            if (game.getCountConnectedUsers() == gameConfig.getUsersCount()) {
                log.info(" >>> Game lobby fulled " + game.getCreatorName());
                List<Long> updatedCache = getCachedGames();
                updatedCache.add(game.getId());
                setCachedGames(updatedCache);
            } else {
                log.info(" === Not fulled game lobby : " + game.getCreatorName() + " " + game.getCountConnectedUsers() +
                        " " + gameConfig.getUsersCount());
            }
        }
    }

//    Every 50sec
//    TODO: PUT INTO CONFIG RATE?
    @Scheduled(fixedRate = 20000)
    public void nextRound() {
//        TODO: NEED CORRECT EXIT WITHOUT EXCEPTION

        for (long gameId : getCachedGames()) {
            log.info("");
            log.info("           try NEXT ROUND");
            GameInfo gameInfo = gameService.getGameInfo(gameId);
            log.info("GameID: " + gameId + ". Round: " + gameInfo.getCurrentRound().getRoundNumber());
            List<User> users = gameInfo.getUsers();
            List<Long> usersMovedId = new ArrayList<>();
            Set<Long> movedUsersIds = gameInfo.getCurrentRound().getUserAndTarget().keySet();
            for (Long id : movedUsersIds) {
                usersMovedId.add(id);
            }
            if (usersMovedId.size() > 0) {
                usersMovedByGame.put(gameId, usersMovedId);
            }

            if (usersMovedByGame.get(gameId) == null) continue;
            log.info("Users moved now: " + usersMovedByGame.get(gameId).size()+  ". Round ID: " + gameInfo.getCurrentRound().getId());
            log.info("Night users %s", gameInfo.getGame().getCountNightUsers());
            log.info("Users move: " + usersMovedByGame.get(gameId));
            if (usersMovedByGame.get(gameId).size() >= gameInfo.getGame().getCountNightUsers()) {
                Game gameWithNewRound = gameService.nextRound(gameId);
                usersMovedByGame.clear();
                log.info(" | ! | New round: " + gameWithNewRound.getRoundNumber());
//                TODO: REPLACE TO METHOD
                if (gameWithNewRound.isFinished()) {
                    log.info(">>>>>>>> GAME OVER creator:" + gameWithNewRound.getCreatorName());
                    log.info("round:" + gameWithNewRound.getRoundNumber() + ", users:" + users);
                    log.info("");
                    List<Long> updatedCache = getCachedGames();
                    updatedCache.remove(gameId);
                    usersMovedByGame.remove(gameId);
                    setCachedGames(updatedCache);
                }
            }
            log.info("");
        }
    }

    @Scheduled(fixedRate = 10000)
    public void nextTurn() {
        for (long gameId : getCachedGames()) {
            GameInfo gameInfo = gameService.getGameInfo(gameId);
            for (int i = 0; i < gameInfo.getGame().getCountNightUsers(); i++) {
                User user = null;
                try {
                    user = gameService.nextUserTurnToGo(gameId);
                } catch (Exception e) {
                    if (e.getClass() == IllegalStateException.class) {
                        gameService.nextRound(gameId);
                    }
                    return;
                }
                if (user.getMoveStatus() == MoveStatus.MOVED) {
                    log.info("");
                    log.info("           NEXT TURN");
                    log.info("User to go " + user.getId());
                    log.info("GameID: " + gameId + ", round: " + gameInfo.getCurrentRound().getId());
                }
            }
        }
    }

    public List<Long> getCachedGames() {
        return cachedGames;
    }

    public void setCachedGames(List<Long> cachedGames) {
        this.cachedGames = cachedGames;
    }

//    TODO: find game with  fulled lobby and store here or db
//    TODO: get user move state every 5 sec
//    TODO: next turn for found games every 30 sec if user not moved
//    TODO: get moved info for games every
//    TODO: next round for not finished games & all night players moved
//    TODO:
}
