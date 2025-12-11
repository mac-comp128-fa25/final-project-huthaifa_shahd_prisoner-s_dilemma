public class TitForTat implements Strategy {

    private Move lastOpponentMove;

    public TitForTat() {
        reset();
    }

    @Override
    public Move makeMove() {
        return lastOpponentMove; // cooperate on first round (default), then mirror
    }

    @Override
    public void recordOpponentMove(Move opponentMove) {
        lastOpponentMove = opponentMove;
    }

    @Override
    public void reset() {
        lastOpponentMove = Move.COOPERATE; // standard TFT opening move
    }

    @Override
    public String getName() {
        return "TitForTat";
    }
}
