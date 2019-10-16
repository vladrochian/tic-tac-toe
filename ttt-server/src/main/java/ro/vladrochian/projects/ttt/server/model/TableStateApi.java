package ro.vladrochian.projects.ttt.server.model;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.Table;

import java.util.ArrayList;
import java.util.List;

public class TableStateApi {
  private List<TableCellApi> cells;

  public TableStateApi(Table table, long state) {
    cells = new ArrayList<>();
    for (Position p : table.getAllPositions()) {
      int player = table.getTableValue(state, p);
      if (player != 0) {
        cells.add(new TableCellApi(p, player));
      }
    }
  }

  public List<TableCellApi> getCells() {
    return cells;
  }

  public void setCells(List<TableCellApi> cells) {
    this.cells = cells;
  }
}
