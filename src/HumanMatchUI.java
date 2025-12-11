import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Human vs Bot UI (Single Match)
 * Matches the exact style of TournamentUI.
 * - Random bot strategy chosen at start
 * - 5 rounds
 * - Human selects moves
 * - Bot uses its strategy
 * - Shows results, payoffs, scores
 * - "Play Again" button to restart
 */
public class HumanMatchUI {

    private static final int ROUNDS_PER_MATCH = 5;

    private final CanvasWindow canvas;
    private GameEngine engine;
    private Player human;
    private Player bot;

    private int currentRound = 1;

    // UI elements
    private GraphicsText matchLabel, roundLabel, moveLabel, payoffLabel, scoreLabel;
    private Button coopButton, defectButton, playAgainButton;

    public HumanMatchUI() {

        canvas = new CanvasWindow("Prisoner's Dilemma — Human vs Bot", 800, 500);

        setupUI();
        startNewMatch();
    }

    // -----------------------------
    // UI SETUP
    // -----------------------------
    private void setupUI() {

        matchLabel = new GraphicsText("");
        matchLabel.setPosition(40, 40);
        canvas.add(matchLabel);

        roundLabel = new GraphicsText("");
        roundLabel.setPosition(40, 70);
        canvas.add(roundLabel);

        moveLabel = new GraphicsText("");
        moveLabel.setPosition(40, 110);
        canvas.add(moveLabel);

        payoffLabel = new GraphicsText("");
        payoffLabel.setPosition(40, 150);
        canvas.add(payoffLabel);

        scoreLabel = new GraphicsText("");
        scoreLabel.setPosition(40, 190);
        canvas.add(scoreLabel);

        coopButton = new Button("Cooperate");
        coopButton.setPosition(40, 250);
        coopButton.onClick(() -> humanMakesMove(Move.COOPERATE));

        defectButton = new Button("Defect");
        defectButton.setPosition(140, 250);
        defectButton.onClick(() -> humanMakesMove(Move.DEFECT));

        playAgainButton = new Button("Play Again");
        playAgainButton.setPosition(40, 300);
        playAgainButton.onClick(this::startNewMatch);
    }

    private void hideAllButtons() {
        try { canvas.remove(coopButton); } catch (Exception ignored) {}
        try { canvas.remove(defectButton); } catch (Exception ignored) {}
        try { canvas.remove(playAgainButton); } catch (Exception ignored) {}
    }

    private void showHumanButtons() {
        hideAllButtons();
        canvas.add(coopButton);
        canvas.add(defectButton);
    }

    private void showPlayAgain() {
        hideAllButtons();
        canvas.add(playAgainButton);
    }

    private void clearUI() {
        moveLabel.setText("");
        payoffLabel.setText("");
        scoreLabel.setText("");
    }

    // -----------------------------
    // NEW MATCH
    // -----------------------------
    private void startNewMatch() {

        // Create players fresh
        human = new Player("Mulham", true);
        bot = createRandomBot();

        // Set up engine
        List<Player> players = new ArrayList<>();
        players.add(human);
        players.add(bot);
        engine = new GameEngine(players);

        // Reset match state
        currentRound = 1;
        human.totalScore = 0;
        bot.totalScore = 0;

        clearUI();

        matchLabel.setText("Match: Mulham vs " + bot.getName());
        roundLabel.setText("Round " + currentRound + " / " + ROUNDS_PER_MATCH);

        showHumanButtons();
    }

    // -----------------------------
    // RANDOM BOT PICKER
    // -----------------------------
    private Player createRandomBot() {

        Random r = new Random();
        int pick = r.nextInt(4);  // number of strategies you include

        switch (pick) {
            case 0 -> { return new Player("Bot: Frank", new TitForTat()); }
            case 1 -> { return new Player("Bot: Bob", new GrimTrigger()); }
            case 2 -> { return new Player("Bot: Claire", StrategyPicker.randomStrategy()); }
            case 3 -> { return new Player("Bot: Ahmed", new AlwaysDefect()); }
        }

        return new Player("Bot: Rami", new RandomMove());
    }

    // -----------------------------
    // HUMAN MOVE HANDLER
    // -----------------------------
    private void humanMakesMove(Move humanMove) {

        RoundRecord rec = engine.playRound(human, bot, humanMove);

        displayRound(rec);

        if (currentRound >= ROUNDS_PER_MATCH) {
            concludeMatch();
        } else {
            currentRound++;
            roundLabel.setText("Round " + currentRound + " / " + ROUNDS_PER_MATCH);
        }
    }

    // -----------------------------
    // DISPLAY UPDATE
    // -----------------------------
    private void displayRound(RoundRecord r) {

        moveLabel.setText(
            r.getP1().getName() + ": " + r.getP1Move() +
            "   vs   " +
            r.getP2().getName() + ": " + r.getP2Move()
        );

        payoffLabel.setText("Payoff: " +
            r.getP1().getName() + " +" + r.getP1Payoff() +
            "   |   " +
            r.getP2().getName() + " +" + r.getP2Payoff()
        );

        scoreLabel.setText(
            "Total Score ⇒ " +
            human.getName() + ": " + human.totalScore + 
            "   |   " +
            bot.getName() + ": " + bot.totalScore
        );
    }

    // -----------------------------
    // MATCH END
    // -----------------------------
    private void concludeMatch() {

        hideAllButtons();

        Player winner = (human.totalScore >= bot.totalScore) ? human : bot;

        matchLabel.setText("Winner: " + winner.getName());
        roundLabel.setText("Match Finished");

        showPlayAgain();
    }
}
