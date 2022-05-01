package com.automafia.automafia.User;

import com.automafia.automafia.Game.Game;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findAllByGameId(long id);

    List<User> findByGameAndAliveStatus(Game game, AliveStatus aliveStatus);
    User findFirstByGameAndMoveStatusIsAndAliveStatus(Game game, MoveStatus status, AliveStatus aliveStatus);
    boolean existsUserByGameAndAliveStatusAndMoveStatus(Game game, AliveStatus aliveStatus, MoveStatus status);
}
