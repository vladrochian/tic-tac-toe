package ro.vladrochian.projects.ttt.server.model;

public class GameSummaryApi {
  private String code;
  private String hostName;
  private int tableHeight;
  private int tableWidth;
  private int lineSize;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
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
}
