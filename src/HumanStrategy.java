import java.util.List;

public class HumanStrategy implements strategy{
    
    private Move nextMove = null;

    public void setNextMove(Move move) {
        this.nextMove = move;
    }

    @Override
    public Move makeMove() {
        // wait for UI input
        while (nextMove == null) {
            Thread.yield();
        }
        Move m = nextMove;
        nextMove = null;
        return m;
    }

    @Override
    public void recordOpponentMove(Move m) {
        // Humans don’t need memory
    }

    @Override
    public String getName() {
        return "Human"; 
    }

    @Override
    public String chooseMove(Player self, List<RoundRecord> history) {
        // This method is not used for HumanStrategy
        return null;
    }
}
