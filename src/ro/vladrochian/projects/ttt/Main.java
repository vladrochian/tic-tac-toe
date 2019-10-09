package ro.vladrochian.projects.ttt;

import ro.vladrochian.projects.ttt.algorithm.Algorithm;
import ro.vladrochian.projects.ttt.algorithm.OptimalAlgorithm;
import ro.vladrochian.projects.ttt.game.Game;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    int mode = args.length > 0 ? Integer.parseInt(args[0]) : getMode();
    if (mode < 0 || mode > 3) {
      mode = 0;
    }
    Algorithm botAlgorithm = new OptimalAlgorithm();
    boolean[] players = {(mode & 1) == 1, (mode >> 1) == 1};
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

  private static int getMode() {
    System.out.println("Game mode: 0 = computer to computer, 1 = join as P1, 2 = join as P2, 3 = two player mode");
    System.out.println("Enter game mode:");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }
}
