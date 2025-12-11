import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.ui.Button;

import java.util.List;

/**
 * Single-elimination tournament UI:
 * - Uses MatchGraph to generate two random semifinals
 * - Semifinal 1 -> Semifinal 2 -> Third-place match -> Final
 * - Human vs Bot: human chooses moves with buttons
 * - Bot vs Bot: "Watch Round" button to step through rounds
 */
public class TournamentUI {

    private static final int ROUNDS_PER_MATCH = 5;

    private final CanvasWindow canvas;
    private final GameEngine engine;

    private Player p1, p2;          // current match players

    private Player semi1P1, semi1P2;
    private Player semi2P1, semi2P2;

    private Player semi1Winner, semi1Loser;
    private Player semi2Winner, semi2Loser;

    private TournamentStage stage;
    private int currentRound = 1;

    // UI elements
    private GraphicsText matchLabel;
    private GraphicsText roundLabel;
    private GraphicsText moveLabel;
    private GraphicsText payoffLabel;
    private GraphicsText scoreLabel;

    private Button coopButton;
    private Button defectButton;
    private Button watchButton;   // for bot vs bot

    public TournamentUI(GameEngine engine) {
        this.engine = engine;
        this.canvas = new CanvasWindow("Prisoner's Dilemma Tournament", 800, 500);

        setupUI();
        initializeBracket();
        startNextMatch();
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

        watchButton = new Button("Watch Round");
        watchButton.setPosition(40, 300);
        watchButton.onClick(this::botVsBotRound);
    }

    private void hideAllButtons() {
        canvas.remove(coopButton);
        canvas.remove(defectButton);
        canvas.remove(watchButton);
    }

    private void showHumanButtons() {
        // hideAllButtons();
        canvas.add(coopButton);
        canvas.add(defectButton);
    }

    private void showWatchButton() {
        // hideAllButtons();
        canvas.add(watchButton);
    }

    private void clearMatchUI() {
        moveLabel.setText("");
        payoffLabel.setText("");
        scoreLabel.setText("");
    }

    // -----------------------------
    // BRACKET SETUP
    // -----------------------------
    private void initializeBracket() {
        // Use MatchGraph to generate random pairs
        List<Pair<Player, Player>> pairs = MatchGraph.generateRandomPairs(engine.players);
        if (pairs.size() < 2) {
            throw new IllegalStateException("Need at least 4 players for two semifinals.");
        }

        Pair<Player, Player> semi1 = pairs.get(0);
        Pair<Player, Player> semi2 = pairs.get(1);

        semi1P1 = semi1.getFirst();
        semi1P2 = semi1.getSecond();

        semi2P1 = semi2.getFirst();
        semi2P2 = semi2.getSecond();

        stage = TournamentStage.SEMIFINAL_1;
    }

    // -----------------------------
    // MATCH FLOW
    // -----------------------------
    private void startNextMatch() {
        // assign current p1, p2 based on stage
        switch (stage) {
            case SEMIFINAL_1 -> {
                p1 = semi1P1;
                p2 = semi1P2;
            }
            case SEMIFINAL_2 -> {
                p1 = semi2P1;
                p2 = semi2P2;
            }
            case THIRD_PLACE -> {
                p1 = semi1Loser;
                p2 = semi2Loser;
            }
            case FINAL -> {
                p1 = semi1Winner;
                p2 = semi2Winner;
            }
            default -> {
                // if DONE, nothing to start
                return;
            }
        }

        // reset per-match state
        currentRound = 1;
        p1.totalScore = 0;
        p2.totalScore = 0;

        clearMatchUI();
        updateLabels();

        // configure buttons based on whether there's a human
        if (matchHasHuman()) {
            showHumanButtons();
        } else {
            showWatchButton();
        }
    }

    private boolean matchHasHuman() {
        return p1.isHuman() || p2.isHuman();
    }

    // -----------------------------
    // HUMAN-vs-BOT ROUND
    // -----------------------------
    private void humanMakesMove(Move humanMove) {
        Player human = p1.isHuman() ? p1 : p2;
        Player bot = p1.isHuman() ? p2 : p1;

        RoundRecord record = engine.playRound(human, bot, humanMove);
        displayRoundResult(record);

        // after this round:
        if (currentRound >= ROUNDS_PER_MATCH) {
            concludeMatch();
        } else {
            currentRound++;
            updateLabels();
        }
    }

    // -----------------------------
    // BOT-vs-BOT ROUND (step-by-step via button)
    // -----------------------------
    private void botVsBotRound() {
        RoundRecord record = engine.playRound(p1, p2);
        displayRoundResult(record);

        if (currentRound >= ROUNDS_PER_MATCH) {
            concludeMatch();
        } else {
            currentRound++;
            updateLabels();
        }
    }

    // -----------------------------
    // END OF MATCH
    // -----------------------------
    private void concludeMatch() {
        // decide winner based on this match's scores
        Player winner = (p1.totalScore >= p2.totalScore) ? p1 : p2;
        Player loser = (winner == p1) ? p2 : p1;

        // hideAllButtons();

        switch (stage) {
            case SEMIFINAL_1 -> {
                semi1Winner = winner;
                semi1Loser = loser;
                stage = TournamentStage.SEMIFINAL_2;
                startNextMatch();
            }
            case SEMIFINAL_2 -> {
                semi2Winner = winner;
                semi2Loser = loser;
                stage = TournamentStage.THIRD_PLACE;
                startNextMatch();
            }
            case THIRD_PLACE -> {
                // third place is 'winner' here; no need to store unless you want
                stage = TournamentStage.FINAL;
                startNextMatch();
            }
            case FINAL -> {
                announceChampion(winner);
                stage = TournamentStage.DONE;
            }
            default -> {
                // nothing
            }
        }
    }

    // -----------------------------
    // DISPLAY HELPERS
    // -----------------------------
    private void displayRoundResult(RoundRecord record) {
        moveLabel.setText(
            record.getP1().getName() + ": " + record.getP1Move()
            + "    vs    "
            + record.getP2().getName() + ": " + record.getP2Move()
        );

        payoffLabel.setText(
            "Payoff: " + record.getP1().getName() + " +" + record.getP1Payoff()
            + "   |   "
            + record.getP2().getName() + " +" + record.getP2Payoff()
        );

        scoreLabel.setText(
            "Total Score => " + p1.getName() + ": " + p1.totalScore
            + "    " + p2.getName() + ": " + p2.totalScore
        );

        updateLabels();
    }

    private void updateLabels() {
        if (p1 != null && p2 != null && stage != TournamentStage.DONE) {
            matchLabel.setText("Match: " + p1.getName() + " vs " + p2.getName());
            roundLabel.setText("Round " + currentRound + " / " + ROUNDS_PER_MATCH);
        }
    }

    private void announceChampion(Player champion) {
        matchLabel.setText("🏆 Tournament Champion: " + champion.getName());
        roundLabel.setText("");
        moveLabel.setText("");
        payoffLabel.setText("");
        scoreLabel.setText("");
        // hideAllButtons();
    }
}
enum TournamentStage {
    SEMIFINAL_1,
    SEMIFINAL_2,
    THIRD_PLACE,
    FINAL,
    DONE
}