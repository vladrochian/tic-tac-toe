package ro.vladrochian.projects.ttt.server.exception;

public class GameNotStartedException extends RuntimeException {
  public GameNotStartedException() {
    super("Game has not started yet");
  }
}
