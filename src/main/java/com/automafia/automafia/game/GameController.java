package com.automafia.automafia.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GameRepository gameRepository;
    private final GameService gameService;

    public GameController(GameRepository gameRepository, GameService gameService) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }

//    @GetMapping
//    ResponseEntity<String> game() {
//        return ResponseEntity.ok().body(gameRepository.findAll().toString());
//    }
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<String> game() {
        return ResponseEntity.ok().body(gameService.getGamesInfo());
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    ResponseEntity<Game> endGame(@RequestParam("id") long id) {
        return ResponseEntity.ok().body(gameService.endGame(id));
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<Integer> gameById(@RequestParam("id") long id) {
        return ResponseEntity.ok().body(gameRepository.findById(id).getGameKey());
    }

    @PostMapping
    ResponseEntity<String> create(@RequestParam("creator") String creatorName) {
        log.info("Try create game. creatorName: " + creatorName);
        if (gameRepository.findByCreatorName(creatorName).size() > 0) {
            return ResponseEntity.badRequest().body("game already created this user");
        }
        Game createdGame = new Game(creatorName);
        gameRepository.save(createdGame);
        log.info("created game:" + createdGame.toString());
        log.info("");
        return ResponseEntity.ok().body(createdGame.toString());
    }
}
