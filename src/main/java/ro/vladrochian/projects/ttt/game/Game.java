package ro.vladrochian.projects.ttt.game;

import ro.vladrochian.projects.ttt.algorithm.Algorithm;
import ro.vladrochian.projects.ttt.player.Bot;
import ro.vladrochian.projects.ttt.player.Human;
import ro.vladrochian.projects.ttt.player.Player;
import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.Table;

public class Game {
  private Player[] players;
  private Table table;
  private long state;

  public Game(boolean[] players, Table table, Algorithm botAlgorithm) {
    assert (players.length == 2);
    this.table = table;
    state = table.initialState();
    this.players = new Player[2];
    for (int i = 0; i < 2; ++i) {
      this.players[i] = players[i] ? new Human("Player " + (i + 1), table) : new Bot("Computer", botAlgorithm);
    }
  }

  private void displayBoard() {
    System.out.println(table.displayState(state));
  }

  public void play() {
    displayBoard();
    while (!table.isGameFinished(state)) {
      Position move = players[table.getCurrentPlayer(state) - 1].getMove(state);
      state = table.getNextState(state, move);
      displayBoard();
    }
  }

  public int winner() {
    return table.getWinner(state);
  }
}
