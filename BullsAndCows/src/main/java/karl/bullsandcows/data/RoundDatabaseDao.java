package karl.bullsandcows.data;

import karl.bullsandcows.models.Round;
import java.sql.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author karl
 */
@Repository
@Profile("database")
// The Database implementation of our Round Dao.
public class RoundDatabaseDao implements RoundDao {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public static final class RoundMapper implements RowMapper<Round> {
        
        // Map result set to Round object.
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            round.setGuess(rs.getString("guess"));
            round.setTime(rs.getTimestamp("time"));
            round.setGuessResult(rs.getString("guessResult"));
            round.setGameId(rs.getInt("gameId"));
            return round;
        }
    }
    
    @Override
    public List<Round> getAllRounds() {
        final String SELECT_ALL_ROUNDS = "SELECT * FROM round;";
        return jdbcTemplate.query(SELECT_ALL_ROUNDS, new RoundMapper());
    }
    
    @Override
    public Round getRoundById(int id) {
        try{
            final String SELECT_ROUND_BY_ID 
                    = "SELECT * FROM round WHERE roundId = ?;";
            return jdbcTemplate.queryForObject(
                    SELECT_ROUND_BY_ID, new RoundMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    @Override
    @Transactional
    public Round addRound(Round round) {
        final String INSERT_ROUND
                = "INSERT INTO round(guess, time, guessResult, gameId)" 
                + " VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(INSERT_ROUND,
                round.getGuess(),
                round.getTime(),
                round.getGuessResult(),
                round.getGameId());
        
        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID();", 
                Integer.class);
        round.setRoundId(newId);
        return round;
    }
    
    @Override
    public boolean updateRound(Round round) {
        final String UPDATE_ROUND
                = "UPDATE round SET " 
                + "guess = ?, " 
                + "time = ?, "
                + "guessResult = ?, "
                + "gameId = ?;";
        
        return jdbcTemplate.update(UPDATE_ROUND, 
                round.getGuess(),
                round.getTime(),
                round.getGuessResult(),
                round.getGameId()) > 0;
    }
    
    @Override
    public boolean deleteRound(int id) {
        final String DELETE_ROUND = "DELETE FROM round WHERE roundId = ?;";
        return jdbcTemplate.update(DELETE_ROUND, id) > 0;
    }
    
}
