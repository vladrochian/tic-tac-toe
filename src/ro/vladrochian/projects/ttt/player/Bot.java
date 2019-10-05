package ro.vladrochian.projects.ttt.player;

import ro.vladrochian.projects.ttt.algorithm.Algorithm;
import ro.vladrochian.projects.ttt.table.Position;

public class Bot implements Player {
  private Algorithm algorithm;

  public Bot(Algorithm algorithm) {
    this.algorithm = algorithm;
  }

  @Override
  public Position getMove(int tableState) {
    System.out.println("Computer's turn");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      System.out.println("Execution interrupted");
    }
    return algorithm.getMove(tableState);
  }
}
