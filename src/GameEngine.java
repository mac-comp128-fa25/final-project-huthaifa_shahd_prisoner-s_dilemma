import java.util.*;

public class GameEngine {

    public ArrayList<player> players = new ArrayList<>();
    public ArrayList<RoundResult> gameHistory = new ArrayList<>();

    // Prisoner's Dilemma payoff matrix (dramatic scoring)
    public static int[] getPayoff(Move a, Move b) {
        if (a == Move.COOPERATE && b == Move.COOPERATE) {
            return new int[] {3, 3};
        }
        if (a == Move.COOPERATE && b == Move.DEFECT) {
            return new int[] {-1, 4};
        }
        if (a == Move.DEFECT && b == Move.COOPERATE) {
            return new int[] {4, -1};
        }
        return new int[] {-2, -2};  // BOTH DEFECT
    }


    /**
     * Plays one round between two players.
     * Gets moves via strategy, updates scores, and returns a RoundResult object.
     */
    public RoundResult playRound(player p1, player p2) {

        // get moves through strategy
        Move move1 = p1.getStrategy().makeMove();
        Move move2 = p2.getStrategy().makeMove();

        // calculate payoff
        int[] payoff = getPayoff(move1, move2);

        // update scores
        p1.totalScore += payoff[0];
        p2.totalScore += payoff[1];

        // create a result object with everything stored inside
        RoundResult result = new RoundResult();
        result.p1 = p1;
        result.p2 = p2;
        result.move1 = move1;
        result.move2 = move2;
        result.payoff1 = payoff[0];
        result.payoff2 = payoff[1];

        // store in round history
        gameHistory.add(result);

        return result;
    }


    /**
     * Runs the full game for N rounds.
     * For now, supports only 2 players (you can expand later).
     */
    public void playManyRounds(int numRounds) {

        if (players.size() < 2) {
            System.out.println("ERROR: Need at least 2 players.");
            return;
        }

        player p1 = players.get(0);
        player p2 = players.get(1);

        for (int round = 1; round <= numRounds; round++) {
            playRound(p1, p2);
        }
    }


    /**
     * Prints final results (used for testing before UI integration)
     */
    public void printFinalScores() {
        System.out.println("=== FINAL SCORES ===");
        for (player p : players) {
            System.out.println(p.getName() + ": " + p.totalScore);
        }
    }


    /**
     * Utility: prints full game history (optional)
     */
    public void printGameHistory() {
        int round = 1;
        for (RoundResult r : gameHistory) {
            System.out.println(
                "Round " + round +
                " | " + r.p1.getName() + " played " + r.move1 +
                " vs " + r.p2.getName() + " played " + r.move2 +
                " | Payoffs: " + r.payoff1 + ", " + r.payoff2
            );
            round++;
        }
    }
}
