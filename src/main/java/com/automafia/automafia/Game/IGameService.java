package com.automafia.automafia.Game;

import java.util.List;

public interface IGameService {
    List<Game> getGamesInfo();

    Integer getGameKey(long id);

    Game startGame(String creator, int usersCount, boolean isManiacExist, boolean isDoctorExist,
                   boolean isMafiaUnanimousDecision);

    Game endGame(long id);

    GameInfo getGameInfo(long id);

    Game nextRound(long id);

    Game connectTo(long gameId, String username);
}
