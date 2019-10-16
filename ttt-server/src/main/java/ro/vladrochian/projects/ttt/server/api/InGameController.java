package ro.vladrochian.projects.ttt.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.vladrochian.projects.ttt.server.model.MoveApi;
import ro.vladrochian.projects.ttt.server.service.InGameService;

@RestController
public class InGameController {
  private InGameService inGameService;

  @Autowired
  public InGameController(InGameService inGameService) {
    this.inGameService = inGameService;
  }

  @GetMapping("/active-game/{userId}")
  public ResponseEntity getGame(@PathVariable String userId) {
    return new ResponseEntity<>(inGameService.getGame(userId), HttpStatus.OK);
  }

  @DeleteMapping("/active-game/{userId}")
  public ResponseEntity leaveGame(@PathVariable String userId) {
    inGameService.leaveGame(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/active-game/{userId}/move")
  public ResponseEntity hasOpponentMoved(@PathVariable String userId) {
    return new ResponseEntity<>(inGameService.hasOpponentMoved(userId), HttpStatus.OK);
  }

  @PostMapping("/active-game/{userId}/move")
  public ResponseEntity performMove(@PathVariable String userId, @RequestBody MoveApi move) {
    return new ResponseEntity<>(inGameService.performMove(userId, move), HttpStatus.CREATED);
  }
}
