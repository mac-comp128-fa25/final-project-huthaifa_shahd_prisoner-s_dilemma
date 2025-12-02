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

    public void recordOpponentMove(Move oppMove) {
        // One defection → defect forever
        if (oppMove == Move.DEFECT) {
            triggered = true;
        }
    }
}
