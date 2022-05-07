package com.automafia.automafia.Game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class GameTasks {

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
}
