package ro.vladrochian.projects.ttt.console.player;

import ro.vladrochian.projects.ttt.table.Position;

public abstract class Player {
  final String name;

  Player(String name) {
    this.name = name;
  }

  public abstract Position getMove(long tableState);
}
