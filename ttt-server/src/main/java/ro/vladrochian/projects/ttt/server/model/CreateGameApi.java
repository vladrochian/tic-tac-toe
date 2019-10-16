package ro.vladrochian.projects.ttt.server.model;

public class CreateGameApi {
  private String hostId;
  private int tableHeight;
  private int tableWidth;
  private int lineSize;
  private boolean gamePublic;

  public String getHostId() {
    return hostId;
  }

  public void setHostId(String hostId) {
    this.hostId = hostId;
  }

  public int getTableHeight() {
    return tableHeight;
  }

  public void setTableHeight(int tableHeight) {
    this.tableHeight = tableHeight;
  }

  public int getTableWidth() {
    return tableWidth;
  }

  public void setTableWidth(int tableWidth) {
    this.tableWidth = tableWidth;
  }

  public int getLineSize() {
    return lineSize;
  }

  public void setLineSize(int lineSize) {
    this.lineSize = lineSize;
  }

  public boolean isGamePublic() {
    return gamePublic;
  }

  public void setGamePublic(boolean gamePublic) {
    this.gamePublic = gamePublic;
  }
}
