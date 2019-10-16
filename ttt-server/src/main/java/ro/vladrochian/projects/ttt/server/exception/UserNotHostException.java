package ro.vladrochian.projects.ttt.server.exception;

public class UserNotHostException extends RuntimeException {
  public UserNotHostException() {
    super("You are not the host of any game");
  }
}
