package ro.vladrochian.projects.ttt.server.exception;

public class IllegalMoveException extends RuntimeException {
  public IllegalMoveException() {
    super("Illegal move");
  }
}
