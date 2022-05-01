package com.automafia.automafia.Game;

import com.automafia.automafia.User.User;
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

    /**
     * GET ALL GAMES INFO
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<List<Game>> game() {
        return ResponseEntity.ok().body(gameService.getGamesInfo());
    }

    /**
     * GET GAME INFO by ID
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<GameInfo> gameInfo(@RequestParam("id") long id) {
        return ResponseEntity.ok().body(gameService.getGameInfo(id));
    }

    /**
     * GET GAME KEY by id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<Integer> gameById(@PathVariable long id) {
        return ResponseEntity.ok().body(gameService.getGameKey(id));
    }

    /**
     * CONNECT TO GAME by ID with USERNAME
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    ResponseEntity<Game> connectTo(@PathVariable long id, @RequestParam("username") String username) {
        return ResponseEntity.ok().body(gameService.connectTo(id, username));
    }

    /**
     * CREATE NEW GAME with CREATOR
     * @return
     */
    @PostMapping
    ResponseEntity<Game> create(
            @RequestParam("creator") String creatorName,
            @RequestParam("usersCount") int usersCount,
            @RequestParam("isManiacExist") boolean isManiacExist,
            @RequestParam("isDoctorExist") boolean isDoctorExist,
            @RequestParam("isMafiaUnanimousDecision") boolean isMafiaUnanimousDecision
    ) {
        return ResponseEntity.ok().body(gameService.startGame(creatorName, usersCount, isManiacExist, isDoctorExist, isMafiaUnanimousDecision));
    }

    /**
     * END GAME by ID
     * @return
     */
    @RequestMapping(value="/{id}/game-over", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    ResponseEntity<Game> endGame(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(gameService.endGame(id));
    }

    /**
     * NEXT ROUND for GAME by ID
     * @return
     */
    @RequestMapping(value="/{id}/next-round", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    ResponseEntity<Game> nextRound(@PathVariable long id) {
        return ResponseEntity.ok().body(gameService.nextRound(id));
    }

    /**
     * NEXT GO for GAME by ID
     * @return
     */
    @RequestMapping(value="/{id}/next-go", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    ResponseEntity<User> nextGo(@PathVariable long id) {
        return ResponseEntity.ok().body(gameService.nextUserTurnToGo(id));
    }
}
