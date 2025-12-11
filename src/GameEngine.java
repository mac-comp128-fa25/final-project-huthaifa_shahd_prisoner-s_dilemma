import java.util.*;

public class GameEngine {

    public List<Player> players = new ArrayList<>();
    public List<RoundRecord> gameHistory = new ArrayList<>();

    public GameEngine(List<Player> players) {
        this.players = players;
    }

    /**
     * ------------------------------
     *  BOT vs BOT ROUND
     * ------------------------------
     */
    public RoundRecord playRound(Player p1, Player p2) {

        // 1. Get bot moves
        Move move1 = p1.getStrategy().makeMove();
        Move move2 = p2.getStrategy().makeMove();

        // 2. Compute payoff
        int[] payoff = getPayoff(move1, move2);

        // 3. Update internal strategy memory (CRITICAL FIX)
        p1.getStrategy().recordOpponentMove(move2);
        p2.getStrategy().recordOpponentMove(move1);

        // 4. Update scores
        p1.totalScore += payoff[0];
        p2.totalScore += payoff[1];

        // 5. Create result record
        RoundRecord record = new RoundRecord(
            p1, p2, move1, move2, payoff[0], payoff[1]
        );

        gameHistory.add(record);
        return record;
    }

    /**
     * ------------------------------
     *  HUMAN vs BOT ROUND
     * ------------------------------
     */
    public RoundRecord playRound(Player human, Player bot, Move humanMove) {

        // safety: guarantee we pass them in correct order
        if (!human.isHuman() || bot.isHuman()) {
            throw new IllegalArgumentException("Order must be (human, bot)");
        }

        // 1. Bot chooses its move
        Move botMove = bot.getStrategy().makeMove();

        // 2. Compute payoff
        int[] payoff = getPayoff(humanMove, botMove);

        // 3. Update bot memory ONLY
        bot.getStrategy().recordOpponentMove(humanMove);

        // 4. Update scores
        human.totalScore += payoff[0];
        bot.totalScore += payoff[1];

        // 5. Record round
        RoundRecord record = new RoundRecord(
            human, bot, humanMove, botMove, payoff[0], payoff[1]
        );

        gameHistory.add(record);
        return record;
    }

    /**
     * ------------------------------
     *  PAYOFF MATRIX
     * ------------------------------
     */
    public static int[] getPayoff(Move m1, Move m2) {
        if (m1 == Move.COOPERATE && m2 == Move.COOPERATE) return new int[]{3, 3};
        if (m1 == Move.COOPERATE && m2 == Move.DEFECT)    return new int[]{0, 5};
        if (m1 == Move.DEFECT && m2 == Move.COOPERATE)    return new int[]{5, 0};
        if (m1 == Move.DEFECT && m2 == Move.DEFECT)       return new int[]{1, 1};
        return new int[]{0, 0}; // fallback
    }

    /**
     * Reset all player scores AND strategy memory
     */
    public void resetGame() {
        for (Player p : players) {
            p.totalScore = 0;
            if (!p.isHuman() && p.getStrategy() != null) {
                p.getStrategy().reset(); 
            }
        }
        gameHistory.clear();
    }
}
