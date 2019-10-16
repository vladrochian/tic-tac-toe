package ro.vladrochian.projects.ttt.server.model;

public class ActiveGameApi {
  private String[] playerNames;
  private TableStateApi tableState;
  private String gameStatus;

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

  public String getGameStatus() {
    return gameStatus;
  }

  public void setGameStatus(String gameStatus) {
    this.gameStatus = gameStatus;
  }
}
