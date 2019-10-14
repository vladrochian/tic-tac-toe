package ro.vladrochian.projects.ttt.console;

import ro.vladrochian.projects.ttt.algorithm.Algorithm;
import ro.vladrochian.projects.ttt.console.player.Bot;
import ro.vladrochian.projects.ttt.console.player.Human;
import ro.vladrochian.projects.ttt.console.player.Player;
import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.Table;

public class ConsoleGame {
  private static final char[] TABLE_SYMBOL = {'.', 'X', 'O'};
  private Player[] players;
  private Table table;
  private long state;

  public ConsoleGame(boolean[] players, Table table, Algorithm botAlgorithm) {
    assert (players.length == 2);
    this.table = table;
    state = table.initialState();
    this.players = new Player[2];
    for (int i = 0; i < 2; ++i) {
      this.players[i] = players[i] ? new Human("Player " + (i + 1), table) : new Bot("Computer", botAlgorithm);
    }
  }

  private String tableToString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 1; i <= table.getHeight(); ++i) {
      for (int j = 1; j <= table.getWidth(); ++j) {
        stringBuilder.append(TABLE_SYMBOL[table.getTableValue(state, Position.from(i, j))]);
      }
      stringBuilder.append('\n');
    }
    return stringBuilder.toString();
  }

  private void displayBoard() {
    System.out.println(tableToString());
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
