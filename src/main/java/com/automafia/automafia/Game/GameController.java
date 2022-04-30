package com.automafia.automafia.Game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<String> game() {
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

    @PostMapping
    ResponseEntity<String> create(@RequestParam("creator") String creatorName) {
        Game created = gameService.startGame(creatorName);
        return ResponseEntity.ok().body(created.toString());
    }
}
