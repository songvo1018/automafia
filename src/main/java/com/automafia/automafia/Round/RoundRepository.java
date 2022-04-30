package com.automafia.automafia.Round;

import com.automafia.automafia.Game.Game;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RoundRepository extends PagingAndSortingRepository<Round, Long> {
    Round findById(@Param("id") long id);
}
