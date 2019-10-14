package ro.vladrochian.projects.ttt.console.player;

import ro.vladrochian.projects.ttt.algorithm.Algorithm;
import ro.vladrochian.projects.ttt.table.Position;

public class Bot extends Player {
  private Algorithm algorithm;

  public Bot(String name, Algorithm algorithm) {
    super(name);
    this.algorithm = algorithm;
  }

  @Override
  public Position getMove(long tableState) {
    System.out.println("Computer's turn");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      System.out.println("Execution interrupted");
    }
    return algorithm.getMove(tableState);
  }
}
