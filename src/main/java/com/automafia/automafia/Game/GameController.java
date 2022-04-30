package com.automafia.automafia.Game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<List<Game>> game() {
        return ResponseEntity.ok().body(gameService.getGamesInfo());
    }

    @RequestMapping(value="/{id}/game-over", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    ResponseEntity<Game> endGame(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(gameService.endGame(id));
    }

    @RequestMapping(value="/{id}/next-round", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    ResponseEntity<Game> nextRound(@PathVariable long id) {
        return ResponseEntity.ok().body(gameService.nextRound(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<Integer> gameById(@PathVariable long id) {
        return ResponseEntity.ok().body(gameService.getGameKey(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    ResponseEntity<Game> connectTo(@PathVariable long id, @RequestParam("username") String username) {
        return ResponseEntity.ok().body(gameService.connectTo(id, username));
    }

    @PostMapping
    ResponseEntity<Game> create(@RequestParam("creator") String creatorName) {
        return ResponseEntity.ok().body(gameService.startGame(creatorName));
    }
}
