package karl.bullsandcows.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author karl
 */
public class Game {
    
    private int gameId;
    private String answer;
    private boolean inProgress;
    // We use game to manage the relationship.
    // Each round assocaited with a game will stored in this array. 
    private List<Round> rounds = new ArrayList<>();
    
    public int getId() {
        return gameId;
    }
    
    public void setId(int gamneId) {
        this.gameId = gamneId;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public boolean getInProgress() {
        return inProgress;
    }
    
    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public List<Round> getRounds() {
        return rounds;
    }
    
    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.gameId;
        hash = 83 * hash + Objects.hashCode(this.answer);
        hash = 83 * hash + (this.inProgress ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.rounds);
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
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.inProgress != other.inProgress) {
            return false;
        }
        if (!Objects.equals(this.answer, other.answer)) {
            return false;
        }
        return Objects.equals(this.rounds, other.rounds);
    }
    
}
