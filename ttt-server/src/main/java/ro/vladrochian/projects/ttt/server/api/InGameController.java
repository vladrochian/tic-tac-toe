package ro.vladrochian.projects.ttt.server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/game")
public class InGameController {
  @GetMapping("/turn/{playerToken}")
  public ResponseEntity getPlayerTurn(@PathVariable String playerToken) {
    return null;
  }
}
