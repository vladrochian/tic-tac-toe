package ro.vladrochian.projects.ttt.algorithm;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.TableState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AStarAlgorithm implements Algorithm {
  {
    initializeGraph();
  }
  private static final int INFINITY = 1000000000;
  enum StateType { WIN, LOSE, DRAW }
  private Map<Integer, Integer> aStarHFunction;
  private Map<Integer, StateType> stateType;
  private Map<Integer, Position> nextMove;

  private void initializeGraph() {
    aStarHFunction = new HashMap<>();
    stateType = new HashMap<>();
    nextMove = new HashMap<>();
    buildGraph(TableState.initialState());
  }

  private void buildGraph(int state) {
    if (TableState.isGameFinished(state)) {
      aStarHFunction.put(state, 0);
      int winner = TableState.getWinner(state);
      if (winner == 0) {
        stateType.put(state, StateType.DRAW);
      } else {
        stateType.put(state, StateType.LOSE);
      }
      return;
    }
    List<Position> edges = TableState.getAvailableMoves(state);
    Position winningMove = null;
    Position drawMove = null;
    for (Position p : edges) {
      int nextState = TableState.getNextState(state, p);
      if (!stateType.containsKey(nextState)) {
        buildGraph(nextState);
      }
      if (stateType.get(nextState).equals(StateType.LOSE)) {
        winningMove = p;
      } else if (stateType.get(nextState).equals(StateType.DRAW)) {
        drawMove = p;
      }
    }
    int bestH = INFINITY;
    Position nextMv = null;
    if (winningMove != null) {
      stateType.put(state, StateType.WIN);
      for (Position p : edges) {
        int nextState = TableState.getNextState(state, p);
        if (stateType.get(nextState).equals(StateType.LOSE) && aStarHFunction.get(nextState) < bestH) {
          bestH = aStarHFunction.get(nextState);
          nextMv = p;
        }
      }
    } else if (drawMove != null) {
      stateType.put(state, StateType.DRAW);
      for (Position p : edges) {
        int nextState = TableState.getNextState(state, p);
        if (stateType.get(nextState).equals(StateType.DRAW) && aStarHFunction.get(nextState) < bestH) {
          bestH = aStarHFunction.get(nextState);
          nextMv = p;
        }
      }
    } else {
      stateType.put(state, StateType.LOSE);
      for (Position p : edges) {
        int nextState = TableState.getNextState(state, p);
        if (aStarHFunction.get(nextState) < bestH) {
          bestH = aStarHFunction.get(nextState);
          nextMv = p;
        }
      }
    }
    aStarHFunction.put(state, bestH + 1);
    nextMove.put(state, nextMv);
  }

  @Override
  public Position getMove(int state) {
    return nextMove.get(state);
  }
}
