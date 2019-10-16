package ro.vladrochian.projects.ttt.server.model;

import ro.vladrochian.projects.ttt.table.Position;

public class TableCellApi {
  private int row, column, player;
  TableCellApi(Position position, int player) {
    row = position.getX();
    column = position.getY();
    this.player = player;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public int getPlayer() {
    return player;
  }

  public void setPlayer(int player) {
    this.player = player;
  }
}
