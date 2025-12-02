public class player {

    private String name;
    private boolean isHuman;
    private strategy strategy;   // the strategy object the player uses
    public int totalScore = 0;   // make public if GameEngine updates directly

    // Constructor for bot
    public player(String name, strategy strategy) {
        this.name = name;
        this.strategy = strategy;
        this.isHuman = false;
    }

    // Constructor for human (strategy can be set later)
    public player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
        this.strategy = null; // will be provided later by UI
    }

    // Constructor for human with assigned strategy
    public player(String name, boolean isHuman, strategy strategy) {
        this.name = name;
        this.isHuman = isHuman;
        this.strategy = strategy;
    }

    // Getters
    public String getName() {
        return name;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public strategy getStrategy() {
        return strategy;
    }

    public int getTotalScore() {
        return totalScore;
    }

    // Setters
    public void setStrategy(strategy strategy) {
        this.strategy = strategy;
    }

    public void addScore(int points) {
        this.totalScore += points;
    }
}
