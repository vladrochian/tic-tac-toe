package ro.vladrochian.projects.ttt.algorithm;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimalAlgorithm extends Algorithm {
  enum StateType { WIN, LOSE, DRAW }
  private Map<Long, StateType> stateType;
  private Map<Long, Position> nextMove;

  public OptimalAlgorithm(Table table) {
    super(table);
    stateType = new HashMap<>();
    nextMove = new HashMap<>();
    buildGraph(table.initialState());
  }

  private void setState(long state, StateType type, Position move) {
    stateType.put(state, type);
    nextMove.put(state, move);
  }

  private void buildGraph(long state) {
    if (table.isGameFinished(state)) {
      int winner = table.getWinner(state);
      if (winner == 0) {
        stateType.put(state, StateType.DRAW);
      } else {
        stateType.put(state, StateType.LOSE);
      }
      return;
    }
    List<Position> availableMoves = table.getAvailableMoves(state);
    Position drawMove = null;
    for (Position p : availableMoves) {
      long nextState = table.getNextState(state, p);
      if (!stateType.containsKey(nextState)) {
        buildGraph(nextState);
      }
      if (stateType.get(nextState).equals(StateType.LOSE)) {
        setState(state, StateType.WIN, p);
        return;
      } else if (stateType.get(nextState).equals(StateType.DRAW) && drawMove == null) {
        drawMove = p;
      }
    }
    if (drawMove != null) {
      setState(state, StateType.DRAW, drawMove);
    } else {
      setState(state, StateType.LOSE, availableMoves.get(0));
    }
  }

  @Override
  public Position getMove(long state) {
    return nextMove.get(state);
  }
}
