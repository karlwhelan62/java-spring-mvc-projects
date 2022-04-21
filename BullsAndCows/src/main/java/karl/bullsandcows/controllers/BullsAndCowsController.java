package karl.bullsandcows.controllers;

import karl.bullsandcows.models.Game;
import karl.bullsandcows.models.Round;
import java.util.List;
import karl.bullsandcows.service.BullsAndCowsServiceLayer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author karl
 */
@RestController
@RequestMapping("/api")
public class BullsAndCowsController {

    private final BullsAndCowsServiceLayer service;
            
    public BullsAndCowsController(BullsAndCowsServiceLayer service) {
        this.service = service;
    }
    
    // Get all games.
    @GetMapping("/game")
    public List<Game> all() {
        return service.getAllGames();
    }
    
    
    // Start a game.
    @PostMapping("/begin")
    public ResponseEntity<Game> createGame() {
        return service.addGame();
    }
    
    // Get game by id.
    @GetMapping("/game/{id}")
    public ResponseEntity<Game> findById(@PathVariable int id) {
        return service.findById(id);
    }
    
    // Get a lsit of the rounds assocaited with a game by id.
    @GetMapping("/rounds/{id}")
    public ResponseEntity<List<Round>> findRoundList(@PathVariable int id) {
        return service.findRoundList(id);
    }
    
    // Play a round by making a guess for a game bu id.
    @PostMapping("/guess")
    public ResponseEntity<Round> makeGuess(@RequestBody Round round) {
        return service.makeGuess(round);
    }
}
