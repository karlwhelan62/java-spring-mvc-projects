package karl.bullsandcows.data;

import java.util.List;
import karl.bullsandcows.models.Round;

/**
 *
 * @author karl
 */
public interface RoundDao {

    // Return a list of all rounds.
    List<Round> getAllRounds();
    
    // Get the round associated with the given round id.
    Round getRoundById(int id);
    
    // Add a round.
    Round addRound(Round round);
    
    // Update a round, return true if successfull.
    boolean updateRound(Round round);
    
    // Delete a round, return true if successfull.
    boolean deleteRound(int id);

}
