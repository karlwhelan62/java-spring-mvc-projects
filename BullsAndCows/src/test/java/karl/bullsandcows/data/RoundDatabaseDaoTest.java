package karl.bullsandcows.data;

import java.sql.Timestamp;
import java.util.List;
import karl.bullsandcows.TestApplicationConfiguration;
import karl.bullsandcows.models.Game;
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
public class RoundDatabaseDaoTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    
    @Before
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        for(Game game : games) {
            gameDao.deleteGame(game.getId());
        }
        
        List<Round> rounds = roundDao.getAllRounds();
        for(Round round : rounds) {
            roundDao.deleteRound(round.getGameId());
        }
    }
    
    /**
     * Test both addRound and getRoundById methods, of class RoundDatabaseDao.
     */
    @Test
    public void testAddGetRound() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setInProgress(true);
        gameDao.addGame(game);
        
        Round round = new Round();
        round.setGuess("4321");
        round.setTime(new Timestamp(1500000000));
        round.setGuessResult("e:4:p:0");
        round.setGameId(game.getId());
        round = roundDao.addRound(round);
        
        Round fromDao = roundDao.getRoundById(round.getRoundId());
        
        assertEquals(round, fromDao);
    }

    /**
     * Test of getAllRounds method, of class RoundDatabaseDao.
     */
    @Test
    public void testGetAllRounds() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setInProgress(true);
        gameDao.addGame(game);
        
        Round round1 = new Round();
        round1.setGuess("4321");
        round1.setTime(new Timestamp(1500000000));
        round1.setGuessResult("e:4:p:0");
        round1.setGameId(game.getId());
        round1 = roundDao.addRound(round1);
        
        
        Round round2 = new Round();
        round2.setGuess("4320");
        round2.setTime(new Timestamp(1500000000));
        round2.setGuessResult("e:3:p:1");
        round2.setGameId(game.getId());
        round2 = roundDao.addRound(round2);
        
        List<Round> rounds = roundDao.getAllRounds();
        
        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round1));
        assertTrue(rounds.contains(round2));
    }

    /**
     * Test of updateRound method, of class RoundDatabaseDao.
     */
    @Test
    public void testUpdateRound() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setInProgress(true);
        game = gameDao.addGame(game);
        
        Round round = new Round();
        round.setGuess("4321");
        round.setTime(new Timestamp(1500000000));
        round.setGuessResult("e:4:p:0");
        round.setGameId(game.getId());
        round = roundDao.addRound(round);
        
        Round fromDao = roundDao.getRoundById(round.getRoundId());
        
        assertEquals(round, fromDao);
        
        round.setGuess("4320");
        
        roundDao.updateRound(round);
        
        assertNotEquals(round, fromDao);
        
        fromDao = roundDao.getRoundById(round.getRoundId());
        
        assertEquals(round, fromDao);
    }

    /**
     * Test of deleteRound method, of class RoundDatabaseDao.
     */
    @Test
    public void testDeleteRound() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setInProgress(true);
        game = gameDao.addGame(game);
        
        Round round = new Round();
        round.setGuess("4321");
        round.setTime(new Timestamp(1500000000));
        round.setGuessResult("e:4:p:0");
        round.setGameId(game.getId());
        round = roundDao.addRound(round);
        
        roundDao.deleteRound(round.getRoundId());
        
        Round fromDao = roundDao.getRoundById(round.getRoundId());
        assertNull(fromDao);
    }
    
}
