package ro.vladrochian.projects.ttt;

import ro.vladrochian.projects.ttt.algorithm.Algorithm;
import ro.vladrochian.projects.ttt.algorithm.RandomAlgorithm;
import ro.vladrochian.projects.ttt.game.Game;

public class Main {
  public static void main(String[] args) {
    Algorithm botAlgorithm = new RandomAlgorithm();
    boolean[] players = {false, false};
    Game game = new Game(players, botAlgorithm);
    game.play();
    int winner = game.winner();
    if (winner == 0) {
      System.out.println("Draw!");
    } else if (players[winner - 1]) {
      System.out.println("Player " + winner + " wins!");
    } else {
      System.out.println("Computer wins!");
    }
  }
}
