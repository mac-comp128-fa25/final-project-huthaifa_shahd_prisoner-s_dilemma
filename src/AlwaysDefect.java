public class AlwaysDefect implements strategy {
    
    public Move makeMove() {
        return Move.DEFECT;
    }
}
