package karl.bullsandcows.data;

import karl.bullsandcows.models.Game;
import karl.bullsandcows.models.Round;
import java.util.List;

/**
 *
 * @author karl
 */
public interface GameDao {
    
    // Returns a list of all games.
    List<Game> getAllGames();
    
    // Returns the game with the given id, or null if it does not exist.
    Game getGameById(int id);
    
    // Gets all the roudns associated with the given game id.
    List<Round> getRoundsForGame(int id);
    
    // Add/start a new game.
    Game addGame(Game game);
    
    // true if game exists and is updated
    boolean updateGame(Game game);
    
    // true if game exists and is deleted
    boolean deleteGame(int id);
}
