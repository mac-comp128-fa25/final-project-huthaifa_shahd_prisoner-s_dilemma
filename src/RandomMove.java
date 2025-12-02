public class RandomMove implements strategy {
    
    @Override
    public Move makeMove() {
        if (Math.random() < 0.5) {
            return Move.COOPERATE;
        } else {
            return Move.DEFECT;
        }
    }

    
    
}
