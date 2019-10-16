package ro.vladrochian.projects.ttt.server.model;

import ro.vladrochian.projects.ttt.algorithm.AStarAlgorithm;
import ro.vladrochian.projects.ttt.algorithm.Algorithm;
import ro.vladrochian.projects.ttt.table.Table;

import java.util.UUID;

public class OnlineGame {
  private String code;
  private String hostId;
  private Table table;
  private Algorithm botAlgorithm;
  private String[] players;
  private boolean gamePublic;
  private boolean started;
  private int[] score;
  private long state;

  public OnlineGame(CreateGameApi gameData) {
    code = UUID.randomUUID().toString().substring(0, 8);
    hostId = gameData.getHostId();
    table = new Table(gameData.getTableHeight(), gameData.getTableWidth(), gameData.getLineSize());
    botAlgorithm = new AStarAlgorithm(table);
    players = new String[2];
    players[0] = hostId;
    gamePublic = gameData.isGamePublic();
    started = false;
    score = new int[2];
    state = table.initialState();
  }

  public String getCode() {
    return code;
  }

  public String getHostId() {
    return hostId;
  }

  public int getTableHeight() {
    return table.getHeight();
  }

  public int getTableWidth() {
    return table.getWidth();
  }

  public int getLineSize() {
    return table.getLineSize();
  }

  public boolean isGamePublic() {
    return gamePublic;
  }

  public boolean isStarted() {
    return started;
  }

  public int[] getScore() {
    return score;
  }

  public Table getTable() {
    return table;
  }

  public long getState() {
    return state;
  }

  public boolean isOpen() {
    return players[0] == null || players[1] == null;
  }

  public String[] getPlayers() {
    return players;
  }

  public void start() {
    started = true;
  }
}
