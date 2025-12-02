import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

/**
 * Kilt Graphics UI for a 2-player Prisoner's Dilemma game.
 * Works directly with your GameEngine.java structure.
 */
public class PDUI {

    private CanvasWindow canvas;
    private GameEngine engine;

    private GraphicsText p1Label, p2Label;
    private GraphicsText roundLabel;
    private GraphicsText moveLabel;
    private GraphicsText payoffLabel;
    private GraphicsText scoreLabel;

    private Button coopButton;
    private Button defectButton;
    private Button nextButton;

    private Move humanMove = null;
    private int round = 1;

    public PDUI(GameEngine engine) {
        this.engine = engine;
        canvas = new CanvasWindow("Prisoner's Dilemma", 800, 500);

        setupUI();
        updateLabels();
    }

    private void setupUI() {
        // Labels
        p1Label = new GraphicsText("Player 1 (Human): " + engine.players.get(0).getName());
        p1Label.setPosition(40, 40);

        p2Label = new GraphicsText("Player 2 (Bot): " + engine.players.get(1).getName());
        p2Label.setPosition(40, 70);

        roundLabel = new GraphicsText("Round: 1");
        roundLabel.setPosition(40, 110);

        moveLabel = new GraphicsText("Choose your move...");
        moveLabel.setPosition(40, 150);

        payoffLabel = new GraphicsText("");
        payoffLabel.setPosition(40, 190);

        scoreLabel = new GraphicsText("");
        scoreLabel.setPosition(40, 230);

        canvas.add(p1Label);
        canvas.add(p2Label);
        canvas.add(roundLabel);
        canvas.add(moveLabel);
        canvas.add(payoffLabel);
        canvas.add(scoreLabel);

        // Buttons
        coopButton = new Button("COOPERATE");
        defectButton = new Button("DEFECT");
        nextButton = new Button("Play Round");

        canvas.add(coopButton, 40, 300);
        canvas.add(defectButton, 200, 300);
        canvas.add(nextButton, 360, 300);

        // Button actions
        coopButton.onClick(() -> {
            humanMove = Move.COOPERATE;
            moveLabel.setText("You selected: COOPERATE");
        });

        defectButton.onClick(() -> {
            humanMove = Move.DEFECT;
            moveLabel.setText("You selected: DEFECT");
        });

        nextButton.onClick(() -> playRound());
    }

    private void updateLabels() {
        roundLabel.setText("Round: " + round);
        scoreLabel.setText(
            engine.players.get(0).getName() + ": " + engine.players.get(0).totalScore +
            "   |   " +
            engine.players.get(1).getName() + ": " + engine.players.get(1).totalScore
        );
    }

    private void playRound() {
        if (humanMove == null) {
            moveLabel.setText("Choose COOPERATE or DEFECT first.");
            return;
        }

        // Bot move from strategy
        Player p1 = engine.players.get(0);
        Player p2 = engine.players.get(1);

        Move botMove = p2.getStrategy().makeMove();

        // Payoffs using GameEngine logic
        int[] payoff = GameEngine.getPayoff(humanMove, botMove);

        p1.totalScore += payoff[0];
        p2.totalScore += payoff[1];

        payoffLabel.setText(
            "You played " + humanMove +
            " | Bot played " + botMove +
            " | Payoffs: " + payoff[0] + ", " + payoff[1]
        );

        // Prepare next round
        round++;
        humanMove = null;
        updateLabels();
    }

    // For testing UI directly
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();

        engine.players.add(new Player("Human", new HumanStrategy()));
        engine.players.add(new Player("Bot", new TitForTat()));

        new PDUI(engine);
    }
}
