Prisoner’s Dilemma Tournament

A Java + Kilt Graphics interactive simulation of strategies, matches, and tournaments

Features

Multiple bot strategies (Tit-for-Tat, Grim Trigger, Random)

Human vs. Bot gameplay

Bot vs. Bot simulation

Full round-robin tournament mode

MatchGraph visualization of all pairings

Modular, extensible strategy architecture

Fully interactive GUI using Kilt Graphics

Project Structure

Core Game Logic
File	Purpose
GameEngine.java	Runs rounds, manages payoffs, updates scores, coordinates moves between players/strategies.
Player.java	Represents a player (name, score, associated strategy).
Move.java	Enum containing COOPERATE and DEFECT.
RoundRecord.java	Stores the actions + payoffs for each round.

Strategy Implementations
Strategy File	Strategy	Behavior
TitForTat.java	Tit-for-Tat	Cooperates first, then copies the opponent’s previous move.
GrimTrigger.java	Grim Trigger	Cooperates until opponent defects once; defects forever afterward.
RandomMove.java	Random	Randomly chooses Cooperate/Defect every round.

All strategies implement:

makeMove();
recordOpponentMove(Move move);

User Interface Components
File	Purpose
TournamentUI.java	Main UI — choose mode, launch matches, run tournament.
HumanMatchUI.java	Interface for Human vs Bot play.
MatchGraph.java	Draws a visual graph of matchups in the tournament.

Built entirely with Kilt Graphics (CanvasWindow, GraphicsText, Button, etc.).

How to Run
Prerequisites

Java 17 or later

Kilt Graphics library added to your classpath

Run the program
java Main


This opens the main menu where you can choose gameplay modes.

-Gameplay Modes
-Human vs Bot

Choose your move (Cooperate or Defect)

Bot responds according to its strategy

UI displays payoffs and score updates

-Bot vs Bot

Two strategies play automatically

Moves + outcomes appear round by round

-Tournament Mode

Every player faces every other player

GameEngine runs all matches

MatchGraph displays the tournament structure visually

Extending the Project

You can easily add:

New strategies ( Pavlov, Forgiving TFT, etc.)

Different payoff matrices

Logging to CSV/JSON

Round animations or charts of cooperation rates

Authors

Huthaifa & Shahd
Macalester College – Final Project
Data Structures / Java Programming