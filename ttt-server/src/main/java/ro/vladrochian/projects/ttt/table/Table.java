package ro.vladrochian.projects.ttt.table;

import java.util.ArrayList;
import java.util.List;

public class Table {
  private final int height;
  private final int width;
  private final int lineSize;
  private final long[] pw3;
  private final Position[] allPositions;

  public Table(int height, int width, int lineSize) {
    this.height = height;
    this.width = width;
    this.lineSize = lineSize;
    int size = height * width;
    pw3 = new long[size];
    pw3[0] = 1;
    for (int i = 1; i < size; ++i) {
      pw3[i] = 3 * pw3[i - 1];
    }
    allPositions = new Position[size];
    for (int i = 0; i < size; ++i) {
      allPositions[i] = decodePosition(i);
    }
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public Position[] getAllPositions() {
    return allPositions;
  }

  public long initialState() {
    return 0;
  }

  private int encodePosition(Position p) {
    return (p.getX() - 1) * width + p.getY() - 1;
  }

  private Position decodePosition(int p) {
    return Position.from(p / width + 1, p % width + 1);
  }

  public int getTableValue(long state, Position p) {
    return (int)((state / pw3[encodePosition(p)]) % 3);
  }

  private int[] getValueCount(long state) {
    int[] cnt = new int[3];
    for (Position p : allPositions) {
      ++cnt[getTableValue(state, p)];
    }
    return cnt;
  }

  public int getCurrentPlayer(long state) {
    int[] count = getValueCount(state);
    assert count[1] == count[2] || count[1] == count[2] + 1 : "State is not valid";
    return count[1] == count[2] ? 1 : 2;
  }

  public long getNextState(long state, Position move) {
    assert getTableValue(state, move) == 0 : "Slot not empty";
    return state + getCurrentPlayer(state) * pw3[encodePosition(move)];
  }

  public long simulateWrongMove(long state, Position move) {
    assert getTableValue(state, move) == 0 : "Slot not empty";
    return state + (3 - getCurrentPlayer(state)) * pw3[encodePosition(move)];
  }

  public List<Position> getAvailableMoves(long state) {
    List<Position> ans = new ArrayList<>();
    for (Position p : allPositions) {
      if (getTableValue(state, p) == 0) {
        ans.add(p);
      }
    }
    return ans;
  }

  private boolean isFull(long state, Position start, int dx, int dy, int player) {
    Position p = start;
    for (int i = 0; i < lineSize; ++i) {
      if (getTableValue(state, p) != player) {
        return false;
      }
      p = Position.from(p.getX() + dx, p.getY() + dy);
    }
    return true;
  }

  private boolean hasFullLine(long state, int player) {
    for (int i = 1; i <= height; ++i) {
      for (int j = 1; j <= width - lineSize + 1; ++j) {
        if (isFull(state, Position.from(i, j), 0, 1, player)) return true;
      }
    }
    for (int i = 1; i <= height - lineSize + 1; ++i) {
      for (int j = 1; j <= width; ++j) {
        if (isFull(state, Position.from(i, j), 1, 0, player)) return true;
      }
    }
    for (int i = 1; i <= height - lineSize + 1; ++i) {
      for (int j = 1; j <= width - lineSize + 1; ++j) {
        if (isFull(state, Position.from(i, j), 1, 1, player)) return true;
      }
    }
    for (int i = 1; i <= height - lineSize + 1; ++i) {
      for (int j = lineSize; j <= width; ++j) {
        if (isFull(state, Position.from(i, j), 1, -1, player)) return true;
      }
    }
    return false;
  }

  private int findFullLine(long state) {
    if (hasFullLine(state, 1)) return 1;
    if (hasFullLine(state, 2)) return 2;
    return 0;
  }

  public boolean isGameFinished(long state) {
    return findFullLine(state) != 0 || getValueCount(state)[0] == 0;
  }

  public int getWinner(long state) {
    assert isGameFinished(state) : "Game is not finished";
    return findFullLine(state);
  }
}
