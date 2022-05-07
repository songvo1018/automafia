package com.automafia.automafia.Game;

import com.automafia.automafia.Game.Config.GameConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GameTasks {

    private List<Long> cachedGames = new ArrayList<>();

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
//    @Scheduled(fixedRate=10000)
    public void printFilledLobby() {
        List<Game> games = gameService.getGamesInfo();
        if (getCachedGames().equals(games)) return;
        for (Game game : games) {
            if (getCachedGames().contains(game.getId())) continue;
            GameConfig gameConfig = game.getGameConfig();
            log.info(" === Not fulled game lobby : " + game.getCreatorName() + " " + game.getCountConnectedUsers() +
                    " " + gameConfig.getUsersCount());
            if (game.getCountConnectedUsers() == gameConfig.getUsersCount()) {
                log.info(" >>> Game lobby fulled" + game.getCreatorName());
                List<Long> updatedCache = getCachedGames();
                updatedCache.add(game.getId());
                setCachedGames(updatedCache);
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
