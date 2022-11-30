package com.automafia.automafia.Game;

import com.automafia.automafia.User.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * GET ALL GAMES INFO
     * @return ResponseEntity<List<Game>> (all not finished games)
     */
    @RequestMapping(value = "/games", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<List<Game>> game() {
        return ResponseEntity.ok().body(gameService.getGamesInfo());
    }

    /**
     * GET GAME INFO by ID
     * @return ResponseEntity<GameInfo> (game which find by id)
     */

    @RequestMapping(value = "/find/{gameId}",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<GameInfo> gameInfo(@PathVariable long gameId) {
        return ResponseEntity.ok().body(gameService.getGameInfo(gameId));
    }

    /**
     * FIND GAME KEY by id
     * @return ResponseEntity<Integer> (key of game)
     */
    @RequestMapping(value = "/key/{gameId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    ResponseEntity<Integer> gameById(@PathVariable long gameId) {
        return ResponseEntity.ok().body(gameService.getGameKey(gameId));
    }

    /**
     * CONNECT TO GAME by ID with USERNAME
     * @return ResponseEntity<Game> (game to which connected user)
     */
    @RequestMapping(value = "/connect/{gameId}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    ResponseEntity<Game> connectTo(
            @PathVariable long gameId,
            @RequestParam("username") String username) {
        if (null == username || "".equals(username) || "" == username) {
            throw new IllegalArgumentException("{\"error\":\"At least one parameter is invalid or not supplied\"}");
        }
        return ResponseEntity.ok().body(gameService.connectTo(gameId, username));
    }

    /**
     * CREATE NEW GAME with CREATOR
     * @return ResponseEntity<Game> (created game)
     */
    @PostMapping
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
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
     * @return ResponseEntity<Game> (ended game)
     */
    @RequestMapping(value="/end-game/{gameId}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    ResponseEntity<Game> endGame(@PathVariable long gameId) {
        return ResponseEntity.ok().body(gameService.endGame(gameId));
    }

    /**
     * NEXT ROUND for GAME by ID
     * @return ResponseEntity<Game> (updated game)
     */
    @RequestMapping(value="/next-round/{gameId}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    ResponseEntity<Game> nextRound(@PathVariable long gameId) {
        return ResponseEntity.ok().body(gameService.nextRound(gameId));
    }

    /**
     * NEXT GO for GAME by ID
     * @return ResponseEntity<User> (next user to go)
     */
    @RequestMapping(value="/next-go/{gameId}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    ResponseEntity<User> nextGo(@PathVariable long gameId) {
        return ResponseEntity.ok().body(gameService.nextUserTurnToGo(gameId));
    }

    /**
     * SELECT TARGET for TURN by USER_ID in GAME by ID
     * @return boolean successfully selection
     */
    @RequestMapping(value = "/target/{gameId}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    ResponseEntity<Boolean> selectTarget(
            @PathVariable long gameId,
            @RequestParam long userId,
            @RequestParam long targetId) {
        return ResponseEntity.ok().body(gameService.selectTargetUser(gameId, userId, targetId));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final String exceptionHandlerIllegalArgumentException(final IllegalArgumentException e) {
        return '"' + e.getMessage() + '"';
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final String exceptionHandlerIllegalStateException(final IllegalStateException e) {
        return '"' + e.getMessage() + '"';
    }
}
