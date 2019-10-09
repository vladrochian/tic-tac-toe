package ro.vladrochian.projects.ttt.algorithm;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.TableState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimalAlgorithm implements Algorithm {
  {
    initializeGraph();
  }
  enum StateType { WIN, LOSE, DRAW }
  private Map<Integer, StateType> stateType;
  private Map<Integer, Position> nextMove;

  private void initializeGraph() {
    stateType = new HashMap<>();
    nextMove = new HashMap<>();
    buildGraph(TableState.initialState());
  }

  private void setState(int state, StateType type, Position move) {
    stateType.put(state, type);
    nextMove.put(state, move);
  }

  private void buildGraph(int state) {
    if (TableState.isGameFinished(state)) {
      int winner = TableState.getWinner(state);
      if (winner == 0) {
        stateType.put(state, StateType.DRAW);
      } else {
        stateType.put(state, StateType.LOSE);
      }
      return;
    }
    List<Position> availableMoves = TableState.getAvailableMoves(state);
    Position winningMove = null;
    Position drawMove = null;
    for (Position p : availableMoves) {
      int nextState = TableState.getNextState(state, p);
      if (!stateType.containsKey(nextState)) {
        buildGraph(nextState);
      }
      if (stateType.get(nextState).equals(StateType.LOSE) && winningMove == null) {
        winningMove = p;
      } else if (stateType.get(nextState).equals(StateType.DRAW) && drawMove == null) {
        drawMove = p;
      }
    }
    if (winningMove != null) {
      setState(state, StateType.WIN, winningMove);
    } else if (drawMove != null) {
      setState(state, StateType.DRAW, drawMove);
    } else {
      setState(state, StateType.LOSE, availableMoves.get(0));
    }
  }

  @Override
  public Position getMove(int state) {
    return nextMove.get(state);
  }
}
