import java.util.List;

public interface strategy {

    Move makeMove();
    void recordOpponentMove(Move m);
    String getName();
    String chooseMove(Player self, List<RoundRecord> history);
}
// Make a random function to choose between the stratigies , IDK yet where it should be 
