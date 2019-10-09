package ro.vladrochian.projects.ttt.algorithm;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.Table;

import java.util.List;

public class AStarAlgorithm extends Algorithm {
  private static final int INFINITY = 1000000000;

  public AStarAlgorithm(Table table) {
    super(table);
  }

  private int closestWall(Position p) {
    int d = Math.min(p.getX(), p.getY());
    d = Math.min(d, table.getHeight() - p.getX() + 1);
    d = Math.min(d, table.getWidth() - p.getY() + 1);
    return d;
  }

  private int centralityFactor(long state) {
    int player = table.getCurrentPlayer(state);
    int ans = 0;
    for (Position p : table.getAllPositions()) {
      int value = table.getTableValue(state, p);
      if (value == player) {
        ans += closestWall(p);
      } else if (value != 0) {
        ans -= closestWall(p);
      }
    }
    return ans;
  }

  private int gFunction(long state) {
    // Position advantage
    return centralityFactor(state);
  }

  private int hFunction(long state) {
    // Further moves advantage
    List<Position> moves = table.getAvailableMoves(state);
    int winningOpportunities = 0;
    for (Position p : moves) {
      long nextState = table.getNextState(state, p);
      if (table.isGameFinished(nextState)) {
        if (table.getWinner(nextState) != 0) {
          return INFINITY;
        }
        return 0;
      }
      boolean doIHaveWinningMoves = false;
      for (Position np : table.getAvailableMoves(nextState)) {
        long checkState = table.getNextState(nextState, np);
        if (table.isGameFinished(checkState) && table.getWinner(checkState) != 0) {
          doIHaveWinningMoves = true;
        }
      }
      if (doIHaveWinningMoves) {
        continue;
      }
      boolean doIHaveMovesAfterwards = false;
      for (Position np : table.getAvailableMoves(nextState)) {
        long nextNextState = table.getNextState(nextState, np);
        boolean canHeWin = false;
        for (Position nnp : table.getAvailableMoves(nextNextState)) {
          long checkState = table.getNextState(nextNextState, nnp);
          if (table.isGameFinished(checkState) && table.getWinner(checkState) != 0) {
            canHeWin = true;
            break;
          }
        }
        if (!canHeWin) {
          doIHaveMovesAfterwards = true;
          break;
        }
      }
      if (!doIHaveMovesAfterwards) {
        return INFINITY;
      }
      long potential = table.simulateWrongMove(state, p);
      if (table.isGameFinished(potential) && table.getWinner(potential) != 0) {
        ++winningOpportunities;
      }
    }
    if (winningOpportunities > 1) return -INFINITY;
    if (winningOpportunities == 1) return -1;
    return 0;
  }

  private int fFunction(long state) {
    if (table.isGameFinished(state)) {
      if (table.getWinner(state) != 0) {
        return -INFINITY;
      }
      return -INFINITY + 200;
    }
    return gFunction(state) + hFunction(state);
  }

  @Override
  public Position getMove(long state) {
    List<Position> availableMoves = table.getAvailableMoves(state);
    Position best = null;
    int bestF = 2 * INFINITY;
    for (Position p : availableMoves) {
      int f = fFunction(table.getNextState(state, p));
      if (f < bestF) {
        best = p;
        bestF = f;
      }
    }
    return best;
  }
}
