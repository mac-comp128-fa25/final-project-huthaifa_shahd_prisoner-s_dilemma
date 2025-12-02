import java.util.List;

public class RandomMove implements strategy {
    
    @Override
    public Move makeMove() {
        if (Math.random() < 0.5) {
            return Move.COOPERATE;
        } else {
            return Move.DEFECT;
        }
    }

    @Override
    public void recordOpponentMove(Move m) {
        // RandomMove does not need to record opponent's move
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public String chooseMove(Player self, List<RoundRecord> history) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'chooseMove'");
    }

    
    
}
