package ro.vladrochian.projects.ttt.table;

import java.util.ArrayList;
import java.util.List;

public class Table {
  private static final char[] SYMBOL = {'.', 'X', 'O'};
  private final int tableHeight;
  private final int tableWidth;
  private final int tableLineSize;
  private final long[] pw3;
  private final Position[] allPositions;

  public Table(int height, int width, int lineSize) {
    tableHeight = height;
    tableWidth = width;
    tableLineSize = lineSize;
    int tableSize = height * width;
    pw3 = new long[tableSize];
    pw3[0] = 1;
    for (int i = 1; i < tableSize; ++i) {
      pw3[i] = 3 * pw3[i - 1];
    }
    allPositions = new Position[tableSize];
    for (int i = 0; i < tableSize; ++i) {
      allPositions[i] = decodePosition(i);
    }
  }

  public String displayState(long state) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 1; i <= tableHeight; ++i) {
      for (int j = 1; j <= tableWidth; ++j) {
        stringBuilder.append(SYMBOL[getTableValue(state, Position.from(i, j))]);
      }
      stringBuilder.append('\n');
    }
    return stringBuilder.toString();
  }

  public long initialState() {
    return 0;
  }

  private int encodePosition(Position p) {
    return (p.getX() - 1) * tableWidth + p.getY() - 1;
  }

  private Position decodePosition(int p) {
    return Position.from(p / tableWidth + 1, p % tableWidth + 1);
  }

  public int getTableValue(long state, Position p) {
    return (int)((state / pw3[encodePosition(p)]) % 3);
  }

  public int[] getValueCount(long state) {
    int[] cnt = new int[3];
    for (Position p : allPositions) {
      ++cnt[getTableValue(state, p)];
    }
    return cnt;
  }

  private void checkValid(long state) {
    int[] count = getValueCount(state);
    assert count[1] == count[2] || count[1] == count[2] + 1 : "State is not valid";
  }

  public int getCurrentPlayer(long state) {
    checkValid(state);
    int[] count = getValueCount(state);
    return count[1] == count[2] ? 1 : 2;
  }

  public long getNextState(long state, Position move) {
    checkValid(state);
    assert getTableValue(state, move) == 0 : "Slot not empty";
    return state + getCurrentPlayer(state) * pw3[encodePosition(move)];
  }

  public List<Position> getAvailableMoves(long state) {
    checkValid(state);
    List<Position> ans = new ArrayList<>();
    for (Position p : allPositions) {
      if (getTableValue(state, p) == 0) {
        ans.add(p);
      }
    }
    return ans;
  }

  private int getLine(long state, Position start, int dx, int dy, int size) {
    int value = getTableValue(state, start);
    Position p = start;
    for (int i = 1; i < size; ++i) {
      p = Position.from(p.getX() + dx, p.getY() + dy);
      if (getTableValue(state, p) != value) {
        return 0;
      }
    }
    return value;
  }

  public int countLines(long state, int size, int player) {
    int cnt = 0;
    for (int i = 1; i <= tableHeight; ++i) {
      for (int j = 1; j <= tableWidth - size + 1; ++j) {
        if (getLine(state, Position.from(i, j), 0, 1, size) == player) ++cnt;
      }
    }
    for (int i = 1; i <= tableHeight - size + 1; ++i) {
      for (int j = 1; j <= tableWidth; ++j) {
        if (getLine(state, Position.from(i, j), 1, 0, size) == player) ++cnt;
      }
    }
    for (int i = 1; i <= tableHeight - size + 1; ++i) {
      for (int j = 1; j <= tableWidth - size + 1; ++j) {
        if (getLine(state, Position.from(i, j), 1, 1, size) == player) ++cnt;
      }
    }
    for (int i = 1; i <= tableHeight - size + 1; ++i) {
      for (int j = size; j <= tableWidth; ++j) {
        if (getLine(state, Position.from(i, j), 1, -1, size) == player) ++cnt;
      }
    }
    return cnt;
  }

  private int findFullLine(long state) {
    if (countLines(state, tableLineSize, 1) == 1) return 1;
    if (countLines(state, tableLineSize, 2) == 1) return 2;
    return 0;
  }

  public boolean isGameFinished(long state) {
    checkValid(state);
    return findFullLine(state) != 0 || getValueCount(state)[0] == 0;
  }

  public int getWinner(long state) {
    assert isGameFinished(state) : "Game is not finished";
    return findFullLine(state);
  }
}
