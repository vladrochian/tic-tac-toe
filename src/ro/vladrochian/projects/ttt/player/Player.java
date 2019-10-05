package ro.vladrochian.projects.ttt.player;

import ro.vladrochian.projects.ttt.table.Position;

public interface Player {
  Position getMove(int tableState);
}
