package ro.vladrochian.projects.ttt.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.vladrochian.projects.ttt.server.service.InGameService;

@RestController("/active-game")
public class InGameController {
  private InGameService inGameService;

  @Autowired
  public InGameController(InGameService inGameService) {
    this.inGameService = inGameService;
  }

  @GetMapping("/{userId}")
  public ResponseEntity getGame(@PathVariable String userId) {
    return new ResponseEntity<>(inGameService.getGame(userId), HttpStatus.OK);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity leaveGame(@PathVariable String userId) {
    inGameService.leaveGame(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{userId}/turn")
  public ResponseEntity getPlayerTurn(@PathVariable String userId) {
    return null;
  }
}
