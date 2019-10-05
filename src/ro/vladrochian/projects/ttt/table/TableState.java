package ro.vladrochian.projects.ttt.table;

import java.util.ArrayList;
import java.util.List;

public class TableState {
  private static final int[] pw3 = new int[9];
  private static final Position[] allPositions = new Position[9];

  static {
    pw3[0] = 1;
    for (int i = 1; i < 9; ++i) {
      pw3[i] = 3 * pw3[i - 1];
    }
    for (int i = 0; i < 9; ++i) {
      allPositions[i] = decodePosition(i);
    }
  }

  public static int initialState() {
    return 0;
  }

  private static int encodePosition(Position p) {
    return (p.getX() - 1) * 3 + p.getY() - 1;
  }

  private static Position decodePosition(int p) {
    return Position.from(p / 3 + 1, p % 3 + 1);
  }

  public static int getTableValue(int state, Position p) {
    return (state / pw3[encodePosition(p)]) % 3;
  }

  private static int[] getValueCount(int state) {
    int[] cnt = new int[3];
    for (Position p : allPositions) {
      ++cnt[getTableValue(state, p)];
    }
    return cnt;
  }

  private static void checkValid(int state) {
    int[] count = getValueCount(state);
    assert count[1] == count[2] || count[1] == count[2] + 1 : "State is not valid";
  }

  public static int getCurrentPlayer(int state) {
    checkValid(state);
    int[] count = getValueCount(state);
    return count[1] == count[2] ? 1 : 2;
  }

  public static int getNextState(int state, Position move) {
    checkValid(state);
    assert getTableValue(state, move) == 0 : "Slot not empty";
    return state + getCurrentPlayer(state) * pw3[encodePosition(move)];
  }

  public static List<Position> getAvailableMoves(int state) {
    checkValid(state);
    List<Position> ans = new ArrayList<>();
    for (Position p : allPositions) {
      if (getTableValue(state, p) == 0) {
        ans.add(p);
      }
    }
    return ans;
  }

  private static int getLine(int state, Position start, int dx, int dy) {
    int value = getTableValue(state, start);
    Position p = start;
    for (int i = 0; i < 2; ++i) {
      p = Position.from(p.getX() + dx, p.getY() + dy);
      if (getTableValue(state, p) != value) {
        return 0;
      }
    }
    return value;
  }

  private static int getRow(int state, int row) {
    return getLine(state, Position.from(row, 1), 0, 1);
  }

  private static int getColumn(int state, int column) {
    return getLine(state, Position.from(1, column), 1, 0);
  }

  private static int getMainDiagonal(int state) {
    return getLine(state, Position.from(1, 1), 1, 1);
  }

  private static int getSecondaryDiagonal(int state) {
    return getLine(state, Position.from(3, 1), -1, 1);
  }

  private static int findFullLine(int state) {
    for (int i = 1; i < 4; ++i) {
      int row = getRow(state, i);
      if (row != 0) return row;
      int column = getColumn(state, i);
      if (column != 0) return column;
    }
    int mainDiag = getMainDiagonal(state);
    if (mainDiag != 0) return mainDiag;
    return getSecondaryDiagonal(state);
  }

  public static boolean isGameFinished(int state) {
    checkValid(state);
    return findFullLine(state) != 0 || getValueCount(state)[0] == 0;
  }

  public static int getWinner(int state) {
    assert isGameFinished(state) : "Game is not finished";
    return findFullLine(state);
  }
}
