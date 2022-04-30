package com.automafia.automafia.Game;

import com.automafia.automafia.Round.Round;
import com.automafia.automafia.Round.RoundService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService implements IGameService {
    private final GameRepository gameRepository;
    private final RoundService roundService;

    public GameService(GameRepository gameRepository, RoundService roundService) {
        this.gameRepository = gameRepository;
        this.roundService = roundService;
    }

    @Override
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

    @Override
    public Integer getGameKey(long id) {
        return gameRepository.findById(id).getGameKey();
    }

    @Override
    public Game startGame(String creator) {
        Game createdGame = new Game(creator);
        Round currentRound = roundService.createNewRound(0);
        createdGame.setCurrentRoundId(currentRound.getId());
        gameRepository.save(createdGame);
        return createdGame;
    }

    @Override
    public Game endGame(long id) {
        Game game = gameRepository.findById(id);
        game.setFinished(true);
        roundService.endLastRound(game.getCurrentRoundId());
        gameRepository.save(game);
        return game;
    }

    public Game nextRound(long id) {
        Game game = gameRepository.findById(id);
        Round lastRound = roundService.endLastRound(game.getCurrentRoundId());
        Round newRound = roundService.createNewRound(lastRound.getRoundNumber());
        game.setCurrentRoundId(newRound.getId());
        gameRepository.save(game);
        return game;
    }
}
