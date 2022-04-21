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
// The in memeory implementation of the Round Dao.
// To use it chnage the profile in the applciations.properties file.
public class RoundInMemoryDao implements RoundDao{
    
    private static final List<Round> rounds = new ArrayList<>();
    
    @Override
    public Round addRound(Round round) {
        
        // Each stream to it's id, get the max and add 1.
        int nextId = rounds.stream()
                .mapToInt(i -> i.getRoundId())
                .max()
                .orElse(0) + 1;
        round.setRoundId(nextId);
        rounds.add(round);
        return round;
    }
    
    @Override
    public List<Round> getAllRounds() {
        return new ArrayList<>(rounds);
    }
    
    @Override
    public Round getRoundById(int id) {
        
        // Filter rounds by id and return, null if not found.
        return rounds.stream()
                .filter(i -> i.getRoundId() == id)
                .findFirst()
                .orElse(null);
    }
     
    
    @Override
    public boolean updateRound(Round round) {
        
        // Increment till we a round with index i or i greater then length.
        int index = 0;
        while(index < rounds.size() 
                && rounds.get(index).getRoundId()!= round.getRoundId()) {
            index ++;
        }
        
        if(index < rounds.size()) {
            rounds.set(index, round);
        }
        
        // If i less than length, round was found.
        return index < rounds.size();
    }
    
    @Override
    public boolean deleteRound(int id) {
        return rounds.removeIf(i -> i.getRoundId()== id);
    }    
}
