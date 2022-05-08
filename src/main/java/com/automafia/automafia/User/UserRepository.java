package com.automafia.automafia.User;

import com.automafia.automafia.Game.Game;
import com.automafia.automafia.User.Roles.Roles;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findAllByGameId(long id);

    List<User> findByGameAndAliveStatusOrAliveStatus(Game game, AliveStatus aliveStatus, AliveStatus secondAliveStatus);
    User findFirstByGameAndMoveStatusIsAndAliveStatusAndRoleTypeIsNot(
            Game game,
            MoveStatus status,
            AliveStatus aliveStatus,
            Roles roleType);
    boolean existsUserByGameAndAliveStatusAndMoveStatusAndRoleTypeNot(Game game, AliveStatus aliveStatus,
                                                                     MoveStatus status, Roles roleType);

    int countUserByGame(Game game);

    List<User> findByGameAndAliveStatusAndRoleTypeNot(Game game, AliveStatus aliveStatus, Roles role);

    List<User> findByGameAndAliveStatusAndRoleType(Game game, AliveStatus aliveStatus, Roles role);

    List<User> findByGameAndAliveStatusNot(Game game, AliveStatus aliveStatus);
}
