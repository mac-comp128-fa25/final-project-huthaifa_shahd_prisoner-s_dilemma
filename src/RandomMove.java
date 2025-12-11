import java.util.Random;

public class RandomMove implements Strategy {

    private Random rand = new Random();

    @Override
    public Move makeMove() {
        return rand.nextBoolean() ? Move.COOPERATE : Move.DEFECT;
    }

    @Override
    public void recordOpponentMove(Move opponentMove) {
        // random has no memory
    }

    @Override
    public void reset() {}

    @Override
    public String getName() {
        return "Random";
    }
}
