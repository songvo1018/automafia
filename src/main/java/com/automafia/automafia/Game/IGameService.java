package com.automafia.automafia.Game;

public interface IGameService {
    String getGamesInfo();

    Integer getGameKey(long id);

    Game startGame(String creator);

    Game endGame(long id);
}
