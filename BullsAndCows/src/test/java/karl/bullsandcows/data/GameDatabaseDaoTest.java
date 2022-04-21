package karl.bullsandcows.data;

import java.sql.Timestamp;
import java.util.List;
import karl.bullsandcows.models.Game;
import karl.bullsandcows.TestApplicationConfiguration;
import karl.bullsandcows.models.Round;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author karl
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDatabaseDaoTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao RoundDao;
    
    
    @Before
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        for(Game game : games) {
            gameDao.deleteGame(game.getId());
        }
        
        List<Round> rounds = RoundDao.getAllRounds();
        for(Round round : rounds) {
            RoundDao.deleteRound(round.getGameId());
        }
    }
    
    /**
     * Test both addGame and getGameById methods, of class GameDatabaseDao.
     */
    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setInProgress(true);
        game = gameDao.addGame(game);
        
        Game fromDao = gameDao.getGameById(game.getId());
        
        assertEquals(game, fromDao);
    }

    /**
     * Test of getAllGames method, of class GameDatabaseDao.
     */
    @Test
    public void testGetAllGames() {
        Game game1 = new Game();
        game1.setAnswer("1234");
        game1.setInProgress(true);
        gameDao.addGame(game1);
        
        Game game2 = new Game();
        game2.setAnswer("4321");
        game2.setInProgress(true);
        gameDao.addGame(game2);
        
        List<Game> games = gameDao.getAllGames();
        
        assertEquals(2, games.size());
        assertTrue(games.contains(game1));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of getRoundsForGame method, of class GameDatabaseDao.
     */
    @Test
    public void testGetRoundsForGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setInProgress(true);
        gameDao.addGame(game);
        
        Round round1 = new Round();
        round1.setGuess("4321");
        round1.setTime(new Timestamp(System.currentTimeMillis()));
        round1.setGuessResult("e:0:p:4");
        round1.setGameId(game.getId());
        RoundDao.addRound(round1);
        game.getRounds().add(round1);
        gameDao.updateGame(game);
        
        Round round2 = new Round();
        round2.setGuess("4320");
        round2.setTime(new Timestamp(System.currentTimeMillis()));
        round2.setGuessResult("e:0:p:3");
        round2.setGameId(game.getId());
        RoundDao.addRound(round2);
        game.getRounds().add(round2);
        gameDao.updateGame(game);
        
        List<Round> rounds = gameDao.getRoundsForGame(game.getId());
        
        assertEquals(2, rounds.size());
    }

    /**
     * Test of updateGame method, of class GameDatabaseDao.
     */
    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setInProgress(true);
        game = gameDao.addGame(game);
        
        Game fromDao = gameDao.getGameById(game.getId());
        
        assertEquals(game, fromDao);
        
        game.setAnswer("4321");
        
        gameDao.updateGame(game);
        
        assertNotEquals(game, fromDao);
        
        fromDao = gameDao.getGameById(game.getId());
        
        assertEquals(game, fromDao);
    }

    /**
     * Test of deleteGame method, of class GameDatabaseDao.
     */
    @Test
    public void testDeleteGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setInProgress(true);
        game = gameDao.addGame(game);
        
        gameDao.deleteGame(game.getId());
        
        Game fromDao = gameDao.getGameById(game.getId());
        assertNull(fromDao);
    }
    
}
