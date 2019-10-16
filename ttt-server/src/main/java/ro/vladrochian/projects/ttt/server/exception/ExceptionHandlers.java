package ro.vladrochian.projects.ttt.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
  @ExceptionHandler({
      AlreadyInGameException.class,
      GameStartedException.class,
      GameNotStartedException.class,
      NotYourTurnException.class,
      PartyFullException.class,
      UserNotHostException.class,
      UserNotInGameException.class
  })
  public ResponseEntity preconditionFailed(RuntimeException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
  }

  @ExceptionHandler({GameNotFoundException.class})
  public ResponseEntity notFoundException(RuntimeException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }
}
