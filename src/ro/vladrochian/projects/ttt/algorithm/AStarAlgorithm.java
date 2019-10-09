package ro.vladrochian.projects.ttt.algorithm;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.Table;

public class AStarAlgorithm extends Algorithm {
  public AStarAlgorithm(Table table) {
    super(table);
  }
//  private static int f(int state) {
//    return g(state) + h(state);
//  }
//
//  private static int g(int state) {
//    // Will return the move count until the current state
//    // As the distance is not that relevant, this should be a tiebreaker between states with a similar h score
//    return TableState.getValueCount(state);
//  }
//
//  private static int h(int state) {
//
//  }

  @Override
  public Position getMove(long state) {
    return null;
  }
}
