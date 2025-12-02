public class Main {
    public static void main(String[] args) {

        GameEngine engine = new GameEngine();

        engine.players.add(new Player("Alice", new RandomMove()));
        engine.players.add(new Player("Bob", new TitForTat()));

        engine.playManyRounds(10);

        

        engine.printGameHistory();
        engine.printFinalScores();
    }
}
