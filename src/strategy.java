public interface Strategy {

    // Decide the next move
    Move makeMove();

    // Update memory with what the opponent just played
    void recordOpponentMove(Move opponentMove);

    // Reset strategy state between tournaments
    void reset();

    // Used by UI to label the strategy
    String getName();
}
