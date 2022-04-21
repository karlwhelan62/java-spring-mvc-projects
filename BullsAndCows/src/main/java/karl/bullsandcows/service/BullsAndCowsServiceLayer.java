package karl.bullsandcows.service;

import java.sql.Timestamp;
import java.util.List;
import karl.bullsandcows.data.GameDao;
import karl.bullsandcows.data.RoundDao;
import karl.bullsandcows.models.Game;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import karl.bullsandcows.models.Round;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author karl
 */
@Service
public class BullsAndCowsServiceLayer {
    
    private final GameDao gameDao;
    private final RoundDao roundDao;
    
    public BullsAndCowsServiceLayer(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }
    
    public List<Game> getAllGames() {
        // We want to hide the answer for games in progress WITHOUT chnaging the
        // answer in the database. The easiest way to do that is to create a new
        // array list and add each game to it. When we come accross 
        List<Game> games = gameDao.getAllGames();
        List<Game> returnedGames = new ArrayList<>();
        for(Game game : games) {
            if(game.getInProgress()) {
                Game newGame = new Game();
                newGame.setAnswer("HIDDEN");
                newGame.setId(game.getId());
                newGame.setInProgress(true);
                newGame.setRounds(game.getRounds());
                returnedGames.add(newGame);
            } else {
                returnedGames.add(game);
            }
        }
        return returnedGames;
    }
    
    // Generates a valid answer.
    private String generateValidAnswer() {
        // We want unique numnbers so use a Set, doesn't allow dupes.
        Set<Integer> numbers = new HashSet<>();
        Random rand = new Random();
        // While the set is less than 4 in length, keep egnerating.
        // If a duplicate is generated, adding it to the Set will not 
        // change the size.
        while (numbers.size() < 4) {
            numbers.add(rand.nextInt(10));
        }
        // Here we use streams and a lamda to convert our set of ints to a 
        // joined string. Map each int to a string and join them with the 
        // empty string.
        return numbers.stream()
                .map(c -> c.toString())
                .collect(Collectors.joining(""));
    }
    
    // Return a ResponseEnity containing the created game.
    
    public ResponseEntity<Game> addGame() {
        Game game = new Game();
        game.setAnswer(generateValidAnswer());
        Game result = gameDao.addGame(game);
        
        // Want to hide the answer.
        // Easiest way to do this is to return a new game object.
        Game returnedGame = new Game();
        returnedGame.setId(result.getId());
        returnedGame.setAnswer("HIDDEN");
        returnedGame.setInProgress(game.getInProgress());
        returnedGame.setRounds(game.getRounds());
        
        return new ResponseEntity<>(returnedGame, HttpStatus.CREATED);
    }
    
    // Return a ResponseEntity with the guess, uncluding the result.
    public ResponseEntity<Round> makeGuess(Round round) {
        
        // If the numbers in the guess are not unique the guess is not valid.
        // Return Unprocessable Entity.
        if(!areNumbersUnique(round.getGuess())) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        int gameId = round.getGameId();
        Game game = gameDao.getGameById(gameId);
        
        // If the given game id does not exist, return Not Found.
        if (game == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        // Get the result of the guess. 
        // position 0 is Exact Matches, position 1 is partial matches. 
        int[] matchList = getMatches(game.getAnswer(), round.getGuess());
        
        // If there are 4 Exact matches the game is over.
        if(matchList[0] == 4) {
            game.setInProgress(false);
        }
        
        // Set the result in the format specified in the assignment spec.
        round.setGuessResult("e:" + String.valueOf(matchList[0]) 
                + ":p:" + String.valueOf(matchList[1]));
        
        // Get the timestamp.
        round.setTime(new Timestamp(System.currentTimeMillis()));
        
        // Add the round, update the assocaited game to contain the round.
        Round newRound = roundDao.addRound(round);
        game.getRounds().add(newRound);
        gameDao.updateGame(game);
        
        return ResponseEntity.ok(newRound);
    }
    
    public ResponseEntity<Game> findById(@PathVariable int id) {
        Game result = gameDao.getGameById(id);
        // If result is null, game not found.
        if(result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        
        // If the game is in progress we want to hide the answer.
        // The easiest way to do that is to create a new object and return that.
        if(result.getInProgress()) {
            Game returnedGame = new Game();
            returnedGame.setAnswer("HIDDEN");
            returnedGame.setId(result.getId());
            returnedGame.setInProgress(result.getInProgress());
            returnedGame.setRounds(result.getRounds());
            return ResponseEntity.ok(returnedGame);
        }
        
        return ResponseEntity.ok(result);
    }
    
    public ResponseEntity<List<Round>> findRoundList(@PathVariable int id) {
        List<Round> result = gameDao.getRoundsForGame(id);
        if(result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
    public boolean areNumbersUnique(String numbers) {
        // Sets can only contain unique numbers so if we convert out string
        // to a set of ints and check if the length is still 4 it will tell us
        // if all our numbers are unqiue.
        return numbers.chars()
                .mapToObj(c -> (int) (c))
                .collect(Collectors.toSet())
                .size() == 4;
    }
    
    // Get the result of a guess.
    public int[] getMatches(String answer, String guess) {
        
        // Turn the answer string to a list of ints.
        List<Integer> answerList = answer
                .chars()
                .mapToObj(c -> (int) c)
                .collect(Collectors.toList());
        
        // Do the same for the guess string.
        List<Integer> guessList = guess
                .chars()
                .mapToObj(c -> (int) c)
                .collect(Collectors.toList());
        
        int exactMatches = 0;
        int partialMatches = 0;
        
        for(int i = 0; i < answerList.size(); i++) {
            // If the element at position i in each list is equal, eaxct match.
            if(answerList.get(i).equals(guessList.get(i))) {
                exactMatches++;
            }
            
            // For each element in guess, if answer contains it, partial match.
            if(answerList.contains(guessList.get(i))) {
                partialMatches++;
            }
        }
        
        // Final partial match count = exact matches - partial matches.
        int[] matchList = new int[]{exactMatches, partialMatches - exactMatches};
        return matchList;
    }
}
