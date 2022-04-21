package karl.bullsandcows.data;

import karl.bullsandcows.models.Game;
import karl.bullsandcows.data.RoundDatabaseDao.RoundMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import karl.bullsandcows.models.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author karl
 */
@Repository
@Profile("database")
// The Database implementation of our Game Dao. 
public class GameDatabaseDao implements GameDao {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    // Maps a result set to a Game object.
    public static final class GameMapper implements  RowMapper<Game> {
        
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setId(rs.getInt("gameId"));
            game.setAnswer(rs.getString("answer"));
            game.setInProgress(rs.getBoolean("inProgress"));
            return game;
        }
    }
    
    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT * FROM game";
        // For each returned row, create an object.
        List<Game> games = jdbcTemplate.query(SELECT_ALL_GAMES, new GameMapper());
        
        // Add the assocaited rounds as a list in the object.
        addRoundsToGames(games);
        
        return games;
    }
    
    // For a list of games, get the roudns for every game.
    private void addRoundsToGames(List<Game> games) {
        for(Game game : games) {
            game.setRounds(getRoundsForGame(game.getId()));
        }
    }
    
    @Override
    public List<Round> getRoundsForGame(int id) {
        // Join game and round on game id for the given id.
        // Returns the rounds assocaited with that game.
        final String SELECT_ROUNDS_FOR_GAME = "SELECT r.* FROM round r "
                + "JOIN game g ON r.gameId = g.gameId WHERE g.gameId = ?";
        return jdbcTemplate.query(
                SELECT_ROUNDS_FOR_GAME, new RoundMapper(), id);
    }
    
    @Override
    public Game getGameById(int id) {
        try {
            final String SELECT_GAME_BY_ID 
                    = "SELECT * FROM game WHERE gameId = ?;";
            // Create Game object
            Game game = jdbcTemplate.queryForObject(SELECT_GAME_BY_ID, 
                    new GameMapper(), id);
            // Set rounds.
            game.setRounds(getRoundsForGame(game.getId()));
            return game;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public Game addGame(Game game) {
        final String INSERT_GAME 
                = "INSERT INTO game(answer, inProgress) VALUES(?, ?);";
        game.setInProgress(true);
        // Insert into the database.
        jdbcTemplate.update(INSERT_GAME,
                game.getAnswer(),
                game.getInProgress());
        
        // Get the last insert id which is the id oof this object.
        int newId = jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class);
        
        game.setId(newId);
        return game;
    }
    
    @Override
    public boolean updateGame(Game game) {
        final String UPDATE_GAME
                = "UPDATE game SET " 
                + "answer = ?, " 
                + "inProgress = ? WHERE gameId = ?;";
        
        return jdbcTemplate.update(UPDATE_GAME, 
                game.getAnswer(),
                game.getInProgress(),
                game.getId()) > 0;
    }
    
    @Override
    public boolean deleteGame(int id) {
        final String DELETE_GAME_ROUNDS
                = "DELETE FROM round WHERE gameId = ?;";
        jdbcTemplate.update(DELETE_GAME_ROUNDS, id);
        
        final String DELETE_GAME = "DELETE FROM game WHERE gameId = ?;";
        return jdbcTemplate.update(DELETE_GAME, id) > 0;
    }
}
