import java.util.List;

public class AlwaysCooperate implements strategy {

    public Move makeMove() {
        return Move.COOPERATE;
    }
    @Override
    public void recordOpponentMove(Move m) {
        // AlwaysCooperate does not need to record opponent's move
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
