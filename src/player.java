public class Player {

    private final String name;
    private final boolean isHuman;
    private Strategy strategy;   // null for humans
    public int totalScore;       // public for UI access, but could make getter

    /**
     * Constructor for bots
     */
    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
        this.isHuman = false;
        this.totalScore = 0;
    }

    /**
     * Constructor for humans
     */
    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
        this.strategy = null;   // humans don't use strategies
        this.totalScore = 0;
    }

    // ---------------------
    // Getters
    // ---------------------
    public String getName() {
        return name;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    // Only bots may change strategy
    public void setStrategy(Strategy s) {
        if (isHuman) {
            throw new IllegalStateException("Humans cannot have strategies.");
        }
        this.strategy = s;
    }

    /**
     * Reset player score AND strategy state between tournaments.
     */
    public void reset() {
        this.totalScore = 0;
        if (!isHuman && strategy != null) {
            strategy.reset();
        }
    }
}
