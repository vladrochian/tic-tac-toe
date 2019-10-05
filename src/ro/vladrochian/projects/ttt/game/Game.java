package ro.vladrochian.projects.ttt.game;

import ro.vladrochian.projects.ttt.algorithm.Algorithm;
import ro.vladrochian.projects.ttt.player.Bot;
import ro.vladrochian.projects.ttt.player.Human;
import ro.vladrochian.projects.ttt.player.Player;
import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.TableState;

public class Game {
  private Player[] players;
  private int state;
  private static final char[] SYMBOL = {'.', 'X', 'O'};

  public Game(boolean[] players, Algorithm botAlgorithm) {
    assert(players.length == 2);
    state = TableState.initialState();
    this.players = new Player[2];
    for (int i = 0; i < 2; ++i) {
      this.players[i] = players[i] ? new Human() : new Bot(botAlgorithm);
    }
  }

  private void displayBoard() {
    for (int i = 1; i < 4; ++i) {
      for (int j = 1; j < 4; ++j) {
        System.out.print(SYMBOL[TableState.getTableValue(state, Position.from(i, j))]);
      }
      System.out.print('\n');
    }
  }

  public void play() {
    displayBoard();
    while (!TableState.isGameFinished(state)) {
      Position move = players[TableState.getCurrentPlayer(state) - 1].getMove(state);
      state = TableState.getNextState(state, move);
      displayBoard();
    }
  }

  public int winner() {
    return TableState.getWinner(state);
  }
}
