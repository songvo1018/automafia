package com.automafia.automafia.Game;

import java.util.List;

public interface IGameService {
    List<Game> getGamesInfo();

    Integer getGameKey(long id);

    Game startGame(String creator);

    Game endGame(long id);
}
