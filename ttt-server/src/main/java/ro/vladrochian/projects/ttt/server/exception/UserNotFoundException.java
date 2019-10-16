package ro.vladrochian.projects.ttt.server.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("User not found");
  }
}
