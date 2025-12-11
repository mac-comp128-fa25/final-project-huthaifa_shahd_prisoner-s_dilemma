import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

public class MenuUI {

    private CanvasWindow canvas;
    private GraphicsText title;
    private Button vsBotButton;
    private Button tournamentButton;
    private Button exitButton;

    public MenuUI() {
        canvas = new CanvasWindow("Prisoner's Dilemma - Menu", 600, 400);

        setupUI();
    }

    private void setupUI() {
        // ----- Title -----
        title = new GraphicsText("Prisoners Dilemma");
        title.setFontSize(32);
        title.setCenter(canvas.getWidth() / 2, 80);
        canvas.add(title);

        // ----- Buttons -----

        // Play vs Bot
        vsBotButton = new Button("Play vs Bot");
        vsBotButton.setCenter(canvas.getWidth() / 2, 160);
        vsBotButton.onClick(() -> {
            new HumanMatchUI();
        });
        canvas.add(vsBotButton);

        // Play Tournament
        tournamentButton = new Button("Play Tournament");
        tournamentButton.setCenter(canvas.getWidth() / 2, 220);
       tournamentButton.onClick(() -> {

            // Build players list (1 human + 3 bots, for example)
            List<Player> players = new ArrayList<>();
            players.add(new Player("Mulham", true));

            // random bots or fixed bots — your choice
            players.add(new Player("Bot 1", StrategyPicker.randomStrategy()));
            players.add(new Player("Bot 2", StrategyPicker.randomStrategy()));
            players.add(new Player("Bot 3", StrategyPicker.randomStrategy()));

            GameEngine engine = new GameEngine(players);
            new TournamentUI(engine);       // launch tournament
        });
        canvas.add(tournamentButton);

        // Exit Game
        exitButton = new Button("Exit Game");
        exitButton.setCenter(canvas.getWidth() / 2, 280);
        exitButton.onClick(() -> {
            canvas.closeWindow();
        });
        canvas.add(exitButton);
    }

}
