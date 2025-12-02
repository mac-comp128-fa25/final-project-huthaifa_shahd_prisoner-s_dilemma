public class RoundRecord {
    public Move move1;
    public Move move2;
    public int payoff1;
    public int payoff2;
    public Player p1;
    public Player p2;

    public RoundRecord playRound(Player p1, Player p2) {

    // get their strategies
    strategy s1 = p1.getStrategy();
    strategy s2 = p2.getStrategy();

    // get moves
    Move move1 = s1.makeMove();
    Move move2 = s2.makeMove();

    // get payoff
    int[] payoff = GameEngine.getPayoff(move1, move2);

    // update player scores
    p1.totalScore += payoff[0];
    p2.totalScore += payoff[1];

    // create result object
    RoundRecord result = new RoundRecord();
    result.p1 = p1;
    result.p2 = p2;
    result.move1 = move1;
    result.move2 = move2;
    result.payoff1 = payoff[0];
    result.payoff2 = payoff[1];

    return result;
}

    public String getOpponentLastMove() {
        return move2 == Move.COOPERATE ? "C" : "D";
    }

}
