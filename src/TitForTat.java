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

    public void recordOpponentMove(Move opponentMove) {
        this.lastOpponentMove = opponentMove;
    }
}
