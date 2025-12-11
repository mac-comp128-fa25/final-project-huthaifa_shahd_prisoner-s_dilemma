public class AlwaysCooperate implements Strategy {

    @Override
    public Move makeMove() {
        return Move.COOPERATE;
    }

    @Override
    public void recordOpponentMove(Move opponentMove) {
        // no memory needed
    }

    @Override
    public void reset() {}

    @Override
    public String getName() {
        return "AlwaysCooperate";
    }
}
