package ro.vladrochian.projects.ttt.algorithm;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.TableState;

import java.util.List;
import java.util.Random;

public class RandomAlgorithm implements Algorithm {
  private Random random;
  {
    random = new Random();
  }

  @Override
  public Position getMove(int state) {
    List<Position> availableMoves = TableState.getAvailableMoves(state);
    return availableMoves.get(random.nextInt(availableMoves.size()));
  }
}
