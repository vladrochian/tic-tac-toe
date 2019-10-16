package ro.vladrochian.projects.ttt.server.exception;

public class UserNotInGameException extends RuntimeException {
  public UserNotInGameException() {
    super("User is not in any game");
  }
}
