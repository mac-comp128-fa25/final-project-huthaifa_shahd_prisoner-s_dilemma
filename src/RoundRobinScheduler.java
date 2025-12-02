import java.util.*;

public class RoundRobinScheduler {

    private ArrayList<Player> players;
    private HashMap<Player, HashSet<Player>> remainingMatches;

    public RoundRobinScheduler(ArrayList<Player> players) {
        this.players = players;
        this.remainingMatches = new HashMap<>();

        // build adjacency list
        for (Player p : players) {
            remainingMatches.put(p, new HashSet<>());
        }

        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                Player a = players.get(i);
                Player b = players.get(j);
                remainingMatches.get(a).add(b);
                remainingMatches.get(b).add(a);
            }
        }
    }

    public boolean hasMoreRounds() {
        for (HashSet<Player> set : remainingMatches.values()) {
            if (!set.isEmpty()) return true;
        }
        return false;
    }

    public ArrayList<Player[]> getNextRound() {

        ArrayList<Player[]> roundPairs = new ArrayList<>();
        HashSet<Player> used = new HashSet<>();

        for (Player p : players) {
            if (used.contains(p)) continue;

            // find a valid opponent
            for (Player opp : remainingMatches.get(p)) {
                if (!used.contains(opp)) {
                    roundPairs.add(new Player[]{p, opp});

                    // mark used
                    used.add(p);
                    used.add(opp);

                    // remove matchup
                    remainingMatches.get(p).remove(opp);
                    remainingMatches.get(opp).remove(p);

                    break;
                }
            }
        }

        return roundPairs;
    }
}
