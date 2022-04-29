package com.automafia.automafia.game;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "games", path = "games")
public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

    List<Game> findByFinished(@Param("finished") Boolean finished);
    List<Game> findByCreatorName(@Param("name") String creatorName);
    Game findById(@Param("id") long id);
}
