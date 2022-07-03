package com.automafia.automafia.User;

import com.automafia.automafia.Game.Game;
import com.automafia.automafia.User.Roles.Roles;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findAllByGameId(long id);

    List<User> findByGameIdAndAliveStatusOrAliveStatus(long gameId, AliveStatus aliveStatus, AliveStatus secondAliveStatus);
    User findFirstByGameIdAndMoveStatusIsAndAliveStatusAndRoleTypeIsNot(
            long gameId,
            MoveStatus status,
            AliveStatus aliveStatus,
            Roles roleType);
    boolean existsUserByGameIdAndAliveStatusAndMoveStatusAndRoleTypeNot(long gameId, AliveStatus aliveStatus,
                                                                        MoveStatus status, Roles roleType);

    int countUserByGameId(long gameId);

    List<User> findByGameIdAndAliveStatusAndRoleTypeNot(long gameId, AliveStatus aliveStatus, Roles role);

    List<User> findByGameIdAndAliveStatusAndRoleType(long gameId, AliveStatus aliveStatus, Roles role);

    List<User> findByGameIdAndAliveStatusNot(long gameId, AliveStatus aliveStatus);
}
