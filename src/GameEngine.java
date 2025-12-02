import java.util.*;

public class GameEngine {

    public ArrayList<Player> players = new ArrayList<>();
    public LinkedList<RoundRecord> gameHistory = new LinkedList<>();

    private ArrayList<Player[]> currentRoundPairs = new ArrayList<>();

    private HashMap<String, String> strategyLabels = new HashMap<>();

    public GameEngine() {
        // Map strategy class names to user-friendly labels
        strategyLabels.put("TitForTat", "Tit For Tat");
        strategyLabels.put("GrimTrigger", "Grim Trigger");
        strategyLabels.put("Random", "Random Bot");
    }


    

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
    public RoundRecord playRound(Player p1, Player p2) {

        

        // get moves through strategy
        String move1 = p1.getStrategy().chooseMove(p1,gameHistory);
        String move2 = p2.getStrategy().chooseMove(p2,gameHistory);

        if (move1 == null || move2 == null) {
            throw new IllegalStateException("Error: A strategy returned a null move.");
        }

        if (!(move1.equals("C") || move1.equals("D"))) {
            throw new IllegalArgumentException("Invalid move by " + p1.getName() + ": " + move1);
        }
        if (!(move2.equals("C") || move2.equals("D"))) {
            throw new IllegalArgumentException("Invalid move by " + p2.getName() + ": " + move2);
        }
        //Convert to Move enum
        Move moveEnum1 = move1.equals("C") ? Move.COOPERATE : Move.DEFECT;
        Move moveEnum2 = move2.equals("C") ? Move.COOPERATE : Move.DEFECT;


        //Record opponent's last move
        p1.getStrategy().recordOpponentMove(moveEnum2);
        p2.getStrategy().recordOpponentMove(moveEnum1);

        // calculate payoff
        int[] payoff = getPayoff(moveEnum1, moveEnum2);

        // update scores
        p1.totalScore += payoff[0];
        p2.totalScore += payoff[1];

        // create a result object with everything stored inside
        RoundRecord result = new RoundRecord();
        result.p1 = p1;
        result.p2 = p2;
        result.move1 = moveEnum1;
        result.move2 = moveEnum2;
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

        if (players == null || players.isEmpty()) {
            throw new IllegalStateException("Error: No players added to the game.");
        }

        if (players.size() == 1) {
            throw new IllegalStateException("Error: A Prisoner's Dilemma requires at least 2 players.");
        }

        for (Player p : players) {
            if (p == null) {
                throw new IllegalStateException("Error: Player list contains a null player.");
            }
            if (p.getStrategy() == null) {
                throw new IllegalStateException("Error: Player " + p.getName() + " has no strategy.");
            }
        }

        if (players.size() < 2) {
            throw new IllegalStateException("ERROR: Need at least 2 players.");
        }

        Player p1 = players.get(0);
        Player p2 = players.get(1);

        for (int round = 1; round <= numRounds; round++) {
            playRound(p1, p2);
        }
    }

    public LinkedList<RoundRecord> getGameHistory() {
    return gameHistory;
    }
    public String getStrategyLabel(Player p) {
        return strategyLabels.get(p.getStrategy().getName());
    }


    public PriorityQueue<Player> getLeaderboard() {
        PriorityQueue<Player> pq = new PriorityQueue<>((a, b) -> b.getTotalScore() - a.getTotalScore());
        pq.addAll(players);
        return pq;
    }
    public void runTournament() {
        RoundRobinScheduler scheduler = new RoundRobinScheduler(players);

        while (scheduler.hasMoreRounds()) {
           currentRoundPairs = scheduler.getNextRound();

            for(Player[] pair : currentRoundPairs) {
                playRound(pair[0], pair[1]);
            }
    }
    }

    public ArrayList<Player[]> getCurrentRoundPairs() {
        return currentRoundPairs;
    }

    public HashMap<String, Integer> getScoreMap() {
        HashMap<String, Integer> map = new HashMap<>();
        for (Player p : players) {
            map.put(p.getName(), p.getTotalScore());
        }
    return map;
    }



    /**
     * Prints final results (used for testing before UI integration)
     */
    public void printFinalScores() {
        System.out.println("=== FINAL SCORES ===");
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.totalScore);
        }
    }


    /**
     * Utility: prints full game history (optional)
     */
    public void printGameHistory() {
        int round = 1;
        for (RoundRecord r : gameHistory) {
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
