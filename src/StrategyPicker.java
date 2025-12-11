import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategyPicker {

    private static final Random rand = new Random();

    public static Strategy randomStrategy() {

        List<Strategy> strategies = new ArrayList<>();
        strategies.add(new TitForTat());
        strategies.add(new GrimTrigger());
        strategies.add(new RandomMove());
        strategies.add(new AlwaysDefect());
        strategies.add(new AlwaysCooperate());   // optional if you have it
        // strategies.add(new PavlovStrategy()); // if added

        int pick = rand.nextInt(strategies.size());
        return strategies.get(pick);
    }
}
