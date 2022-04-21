package karl.bullsandcows.data;

import karl.bullsandcows.models.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
/**
 *
 * @author karl
 */
@Repository
@Profile("memory")
// An in memory implementation of our Game dao.
// Can be used by changing the profile in the application.properties file.
public class GameInMemoryDao implements GameDao {
    
    private static final List<Game> games = new ArrayList<>();
    
    @Override
    public Game addGame(Game game) {
        
        // Map each game to it's id, get the max and add 1.
        // If no games in the list return 0 (and add 1)
        int nextId = games.stream()
                .mapToInt(i -> i.getId())
                .max()
                .orElse(0) + 1;
        
        // Set the values and add to Dao.
        game.setId(nextId);
        game.setInProgress(true);
        List<Round> rounds = new ArrayList<>();
        game.setRounds(rounds);
        games.add(game);
        
        return game;
    }
    
    @Override
    public List<Game> getAllGames() {
        return new ArrayList<>(games);
    }
    
    @Override
    public Game getGameById(int id) {
        // Filter by id and return, if id not in Dao return null.
        return games.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Round> getRoundsForGame(int id) {
        // Filter by id, map to getRounds and return.
        return games.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .map(i -> i.getRounds())
                .orElse(null);
    } 
    
    @Override
    public boolean updateGame(Game game) {
        
        // Increment till we find game with index i or i greater then length.
        int index = 0;
        while(index < games.size() 
                && games.get(index).getId() != game.getId()) {
            index ++;
        }
        
        if(index < games.size()) {
            games.set(index, game);
        }
        
        // If I less than length, game found.
        return index < games.size();
    }
    
    @Override
    public boolean deleteGame(int id) {
        return games.removeIf(i -> i.getId() == id);
    }
}
