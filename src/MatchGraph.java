import java.util.*;

/**
 * MatchGraph represents a set of players as nodes
 * and their scheduled matchups as edges.
 * 
 * Used to generate round-robin tournaments or random pairings
 * for the Prisoner's Dilemma simulator.
 */
public class MatchGraph {

    // Adjacency list: Player -> List of opponents
    private Map<Player, List<Player>> adjList;

    public MatchGraph() {
        adjList = new HashMap<>();
    }

    // Adds a player node to the graph if not already present
    public void addPlayer(Player p) {
        adjList.putIfAbsent(p, new ArrayList<>());
    }

    // Adds an undirected edge (two players must play each other)
    public void addMatch(Player a, Player b) {
        adjList.putIfAbsent(a, new ArrayList<>());
        adjList.putIfAbsent(b, new ArrayList<>());

        if (!adjList.get(a).contains(b)) adjList.get(a).add(b);
        if (!adjList.get(b).contains(a)) adjList.get(b).add(a);
    }

    // Returns all opponents of a player
    public List<Player> getOpponents(Player p) {
        return adjList.getOrDefault(p, new ArrayList<>());
    }

    // Generates a full round-robin tournament (every player plays every other player)
    public void generateFullRoundRobin(List<Player> players) {

        // Make sure all players are in the graph
        for (Player p : players) {
            addPlayer(p);
        }

        // Create all unique pairs
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                addMatch(players.get(i), players.get(j));
            }
        }
    }

    /**
     * Extracts all matches (unique pairs) as a list.
     * Ensures (A,B) and (B,A) are not duplicated.
     */
    public List<Pair<Player, Player>> getAllMatches() {
        List<Pair<Player, Player>> matches = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        for (Player a : adjList.keySet()) {
            for (Player b : adjList.get(a)) {

                String key1 = a.getName() + "-" + b.getName();
                String key2 = b.getName() + "-" + a.getName();

                if (!seen.contains(key1) && !seen.contains(key2)) {
                    matches.add(new Pair<>(a, b));
                    seen.add(key1);
                }
            }
        }

        return matches;
    }

    /**
     * Generates random 1v1 pairings for a single round.
     * Does NOT store them in the graph (used for randomized tournaments).
     */
    public static List<Pair<Player, Player>> generateRandomPairs(List<Player> players) {
        Collections.shuffle(players);

        List<Pair<Player, Player>> pairs = new ArrayList<>();

        for (int i = 0; i < players.size() - 1; i += 2) {
            pairs.add(new Pair<>(players.get(i), players.get(i + 1)));
        }

        // If odd number of players, last one gets no match this round
        return pairs;
    }
}




