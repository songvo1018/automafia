package com.automafia.automafia.game;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public String getGamesInfo() {
        List<Game> games = gameRepository.findByFinished(false);
        List<String> gamesInfo = new ArrayList<String>();
        if (games.size() > 0) {
            for (Game game : games) {
                gamesInfo.add(game.toString());
            }
            return gamesInfo.toString();
        }
        return "Games not created";
    }

    public Game endGame(long id) {
        Game game = gameRepository.findById(id);
        game.setFinished(true);
        gameRepository.save(game);
        return game;
    }
}
