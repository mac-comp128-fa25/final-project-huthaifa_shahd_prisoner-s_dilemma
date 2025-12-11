public class AlwaysDefect implements Strategy {

    @Override
    public Move makeMove() {
        return Move.DEFECT;
    }

    @Override
    public void recordOpponentMove(Move opponentMove) {
        // no memory needed
    }

    @Override
    public void reset() {}

    @Override
    public String getName() {
        return "AlwaysDefect";
    }
}
