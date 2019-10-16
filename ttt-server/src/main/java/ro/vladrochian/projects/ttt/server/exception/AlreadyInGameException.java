package ro.vladrochian.projects.ttt.server.exception;

public class AlreadyInGameException extends RuntimeException {
  public AlreadyInGameException() {
    super("Already in a game");
  }
}
