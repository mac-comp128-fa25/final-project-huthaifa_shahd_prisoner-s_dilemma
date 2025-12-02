import java.util.List;

public class TitForTat implements strategy {
    
    private Move lastOpponentMove;

    public TitForTat() {
        this.lastOpponentMove = null; // No move made yet
    }

    public Move makeMove() {
        if (lastOpponentMove == null) {
            return Move.COOPERATE; // First move is always cooperate
        }
        return lastOpponentMove; // Mimic opponent's last move
    }

    @Override
    public void recordOpponentMove(Move opponentMove) {
        this.lastOpponentMove = opponentMove;
    }
    @Override
    public String chooseMove(Player self, List<RoundRecord> history) {
        if (history.isEmpty()) return "C";
        return history.get(history.size() - 1).getOpponentLastMove();
    }

    @Override
    public String getName() {
        return "TitForTat";
    }
}

