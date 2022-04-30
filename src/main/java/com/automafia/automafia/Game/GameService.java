package com.automafia.automafia.Game;

import com.automafia.automafia.Round.Round;
import com.automafia.automafia.Round.RoundService;
import org.springframework.stereotype.Service;

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
}
