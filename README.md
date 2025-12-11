Prisoner’s Dilemma Tournament – README
Overview

This project implements an interactive Prisoner’s Dilemma simulation using Kilt Graphics in Java.
Users can play against bot strategies, watch bots play against each other, and run full tournament simulations.

The system includes:

A Game Engine that controls scoring, rounds, and interactions between strategies.

Multiple bot strategies (Tit-for-Tat, Grim Trigger, Random, etc.).

Human-play UI windows for individual matches.

A full Tournament UI and a Match Graph that visualizes pairings and results.

Project Structure
Core Game Logic
File	Description
GameEngine.java	Manages rounds, scoring, player turns, and strategy interactions. Handles match flow and updates player scores.
Player.java	Represents a player (human or bot). Stores name, score, and strategy associated with that player.
Move.java	Enum representing the two possible actions: COOPERATE and DEFECT.
RoundRecord.java	Stores the result of a single round (moves of each player + payoff). Useful for UI display or analysis.
Strategies
File	Strategy	Description
TitForTat.java	Tit-for-Tat	Starts with cooperation; then copies opponent’s previous move.
GrimTrigger.java	Grim Trigger	Cooperates until opponent defects once—then defects forever.
RandomMove.java	Random	Chooses Cooperate or Defect randomly each round.

Each strategy implements the same interface pattern:

chooseMove()

recordOpponentMove(Move move)
so the engine can interact with all strategies in a unified way.

User Interface Components
File	Description
TournamentUI.java	Main menu + tournament runner. Lets you choose: Play vs Bot, Bot vs Bot, Full Tournament. Handles button interactions and canvas updates.
HumanMatchUI.java	UI for human vs. bot matches. Displays buttons to choose moves and shows payoffs per round.
MatchGraph.java	Displays a graph-like visualization of which players play against each other in the tournament. Useful for debugging and demonstration.

All UIs are built using Kilt Graphics (CanvasWindow, Button, GraphicsText, etc.).

How to Run the Program

Open the project in IntelliJ, Eclipse, or run through the terminal.

Ensure the Kilt Graphics library is included in your classpath.

Run:

java TournamentUI


OR in your IDE, run the TournamentUI class.

This opens the main menu window.

Gameplay Modes
1. Play vs Bot

Human selects moves using UI buttons.

Bot uses its defined strategy (Tit-for-Tat, Grim Trigger, Random).

Game engine updates score and displays round outcomes.

2. Bot vs Bot Match

Two automated strategies play multiple rounds.

You can watch moves and outcomes appear on screen.

No human input required.

3. Full Tournament

All strategies and players are paired round-robin style.

TournamentUI shows results.

MatchGraph draws edges showing who played whom.

Dependencies

Kilt Graphics Library
Required for all UI components.
Make sure the JAR is properly included under Project Structure → Libraries.

Common Issues & Fixes
NullPointerException: Player.getStrategy() is null

Occurs if a bot Player is created without assigning a strategy.
Fix: ensure every bot is initialized like:

new Player("Bot 1", new TitForTat());

Buttons don’t disable

Kilt Graphics does not support setEnabled(false).
To disable interaction, remove the button from the canvas and re-add it later if needed.

Future Improvements

Add more strategy types (Pavlov, Always Defect, Always Cooperate).

Export tournament results to a file.

Add customizable payoff matrices.

Animated visualization of Prisoner’s Dilemma payoff evolution.

Authors

Huthaifa & Shahd
Macalester College — CS 125/225 Final Project
Fall 2024 / Fall 2025