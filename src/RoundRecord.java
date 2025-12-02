public class RoundRecord {
    int round;
    player player1;
    player player2;
    Move move1;
    Move move2;
    int score1;
    int score2;

    public RoundRecord(int round, player player1, player player2, Move move1, Move move2, int score1, int score2) {
        this.round = round;
        this.player1 = player1;
        this.player2 = player2;
        this.move1 = move1;
        this.move2 = move2;
        this.score1 = score1;
        this.score2 = score2;
    }
    public int getRound() {
        return round;
    }
    
}
