package ro.vladrochian.projects.ttt.server.exception;

public class GameStartedException extends RuntimeException {
  public GameStartedException() {
    super("Game has started");
  }
}
