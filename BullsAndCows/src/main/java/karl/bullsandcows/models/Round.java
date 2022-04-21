package karl.bullsandcows.models;

import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author karl
 */
public class Round {
    
    private int roundId;
    private String guess;
    private Timestamp time;
    private int gameId;
    private String guessResult;
    
    public int getRoundId() {
        return roundId;
    }
    
    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }
    
    public String getGuess() {
        return guess;
    }
    
    public void setGuess(String guess) {
        this.guess = guess;
    }
    
    public Timestamp getTime() {
        return time;
    }
    
    public void setTime(Timestamp time) {
        this.time = time;
    }
    
    public int getGameId() {
        return gameId;
    }
    
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    
    public String getGuessResult() {
        return guessResult;
    }
    
    public void setGuessResult(String guessResult) {
        this.guessResult = guessResult;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.roundId;
        hash = 31 * hash + Objects.hashCode(this.guess);
        hash = 31 * hash + Objects.hashCode(this.time);
        hash = 31 * hash + this.gameId;
        hash = 31 * hash + Objects.hashCode(this.guessResult);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.guessResult, other.guessResult)) {
            return false;
        }
        return Objects.equals(this.time, other.time);
    }
    
}
