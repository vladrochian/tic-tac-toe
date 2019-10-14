package ro.vladrochian.projects.ttt;

import ro.vladrochian.projects.ttt.algorithm.AStarAlgorithm;
import ro.vladrochian.projects.ttt.algorithm.Algorithm;
import ro.vladrochian.projects.ttt.algorithm.OptimalAlgorithm;
import ro.vladrochian.projects.ttt.algorithm.RandomAlgorithm;
import ro.vladrochian.projects.ttt.game.Game;
import ro.vladrochian.projects.ttt.table.Table;

import java.util.Scanner;

public class ConsoleApplication {
  public static void main(String[] args) {
    int mode = args.length > 0 ? Integer.parseInt(args[0]) : readMode();
    if (mode < 0 || mode > 3) {
      mode = 0;
    }

    int tableRows = args.length > 1 ? Integer.parseInt(args[1]) : 3;
    int tableColumns = args.length > 2 ? Integer.parseInt(args[2]) : 3;
    int tableLineSize = args.length > 3 ? Integer.parseInt(args[3]) : 3;
    Table table = new Table(tableRows, tableColumns, tableLineSize);

    String algorithmName = args.length > 4 ? args[4] : "optimal";

    boolean[] players = {(mode & 1) == 1, (mode >> 1) == 1};
    Game game = new Game(players, table, getAlgorithm(algorithmName, table));
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

  private static int readMode() {
    System.out.println("Game mode: 0 = computer to computer, 1 = join as P1, 2 = join as P2, 3 = two player mode");
    System.out.println("Enter game mode:");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }

  private static Algorithm getAlgorithm(String name, Table table) {
    switch (name) {
      case "random":
        return new RandomAlgorithm(table);
      case "astar":
        return new AStarAlgorithm(table);
      default:
        return new OptimalAlgorithm(table);
    }
  }
}
