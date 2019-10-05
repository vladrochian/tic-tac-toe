package ro.vladrochian.projects.ttt.table;

public class Position {
  private int x, y;

  private Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static Position from(int x, int y) {
    return new Position(x, y);
  }

  int getX() {
    return x;
  }

  int getY() {
    return y;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Position)) return false;
    Position p = (Position)obj;
    return p.getX() == getX() && p.getY() == getY();
  }
}
