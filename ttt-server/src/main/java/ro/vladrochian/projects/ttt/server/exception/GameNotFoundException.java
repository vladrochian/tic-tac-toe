package ro.vladrochian.projects.ttt.server.exception;

public class GameNotFoundException extends RuntimeException {
  public GameNotFoundException() {
    super("Game not found");
  }
}
