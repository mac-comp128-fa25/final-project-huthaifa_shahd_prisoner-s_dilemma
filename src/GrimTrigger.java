import java.util.List;

public class GrimTrigger implements strategy {

    private boolean triggered = false; // becomes true after opponent defects once

    @Override
    public Move makeMove() {
        // If we have been "triggered", always defect
        if (triggered) {
            return Move.DEFECT;
        }

        // Otherwise, start by cooperating
        return Move.COOPERATE;
    }
    @Override
    public void recordOpponentMove(Move oppMove) {
        // One defection → defect forever
        if (oppMove == Move.DEFECT) {
            triggered = true;
        }
    }
    @Override
    public String getName() {
        return "GrimTrigger";
    }
    @Override
    public String chooseMove(Player self, List<RoundRecord> history) {
        for (RoundRecord record : history) {
            if (record.getOpponentLastMove().equals("D")) {
                return "D";
            }
        }
        return "C";
    }
}
