package ro.vladrochian.projects.ttt.server.exception;

public class PartyFullException extends RuntimeException {
  public PartyFullException() {
    super("Party is full");
  }
}
