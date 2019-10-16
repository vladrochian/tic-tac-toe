package ro.vladrochian.projects.ttt.server.model;

public class ActiveGameApi {
  private int[] score;
  private String[] playerNames;
  private TableStateApi tableState;
  
  public int[] getScore() {
    return score;
  }

  public void setScore(int[] score) {
    this.score = score;
  }

  public String[] getPlayerNames() {
    return playerNames;
  }

  public void setPlayerNames(String[] playerNames) {
    this.playerNames = playerNames;
  }

  public TableStateApi getTableState() {
    return tableState;
  }

  public void setTableState(TableStateApi tableState) {
    this.tableState = tableState;
  }
}
