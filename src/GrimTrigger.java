public class GrimTrigger implements Strategy {

    private boolean triggered;

    public GrimTrigger() {
        reset();
    }

    @Override
    public Move makeMove() {
        return triggered ? Move.DEFECT : Move.COOPERATE;
    }

    @Override
    public void recordOpponentMove(Move opponentMove) {
        if (opponentMove == Move.DEFECT) {
            triggered = true;
        }
    }

    @Override
    public void reset() {
        triggered = false;
    }

    @Override
    public String getName() {
        return "GrimTrigger";
    }
}
