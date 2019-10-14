package ro.vladrochian.projects.ttt.algorithm;

import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.Table;

public abstract class Algorithm {
  protected Table table;

  Algorithm(Table table) {
    this.table = table;
  }

  public abstract Position getMove(long state);
}
