package ro.vladrochian.projects.ttt.algorithm;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.Table;

import java.util.List;
import java.util.Random;

public class RandomAlgorithm extends Algorithm {
  private Random random;

  public RandomAlgorithm(Table table) {
    super(table);
    random = new Random();
  }

  @Override
  public Position getMove(long state) {
    List<Position> availableMoves = table.getAvailableMoves(state);
    return availableMoves.get(random.nextInt(availableMoves.size()));
  }
}
