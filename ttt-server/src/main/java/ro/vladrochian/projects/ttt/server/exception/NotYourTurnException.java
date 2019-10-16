package ro.vladrochian.projects.ttt.server.exception;

public class NotYourTurnException extends RuntimeException {
  public NotYourTurnException() {
    super("Not your turn");
  }
}
