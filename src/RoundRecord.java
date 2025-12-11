public class RoundRecord {

    private final Player p1;
    private final Player p2;

    private final Move p1Move;
    private final Move p2Move;

    private final int p1Payoff;
    private final int p2Payoff;

    public RoundRecord(Player p1, Player p2,
                       Move p1Move, Move p2Move,
                       int p1Payoff, int p2Payoff) {

        this.p1 = p1;
        this.p2 = p2;
        this.p1Move = p1Move;
        this.p2Move = p2Move;
        this.p1Payoff = p1Payoff;
        this.p2Payoff = p2Payoff;
    }

    public Player getP1() { return p1; }
    public Player getP2() { return p2; }

    public Move getP1Move() { return p1Move; }
    public Move getP2Move() { return p2Move; }

    public int getP1Payoff() { return p1Payoff; }
    public int getP2Payoff() { return p2Payoff; }
}
