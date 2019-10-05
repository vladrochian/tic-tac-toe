package ro.vladrochian.projects.ttt.algorithm;

import ro.vladrochian.projects.ttt.table.Position;

public interface Algorithm {
  Position getMove(int state);
}
