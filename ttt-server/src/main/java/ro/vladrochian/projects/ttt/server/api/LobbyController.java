package ro.vladrochian.projects.ttt.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.vladrochian.projects.ttt.server.model.CreateGameApi;
import ro.vladrochian.projects.ttt.server.service.LobbyService;

@RestController
public class LobbyController {
  private LobbyService lobbyService;

  @Autowired
  public LobbyController(LobbyService lobbyService) {
    this.lobbyService = lobbyService;
  }

  @GetMapping("/lobby/games")
  public ResponseEntity getGameList() {
    return new ResponseEntity<>(lobbyService.getPublicGames(), HttpStatus.OK);
  }

  @PostMapping("/lobby/games")
  public ResponseEntity createGame(@RequestBody CreateGameApi game) {
    return new ResponseEntity<>(lobbyService.createGame(game), HttpStatus.CREATED);
  }

  @PostMapping("/lobby/games/{gameCode}/opponent")
  public ResponseEntity joinGame(@PathVariable String gameCode, @RequestBody String userId) {
    lobbyService.joinGame(gameCode, userId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @DeleteMapping("/lobby/my-game/{hostId}/opponent")
  public ResponseEntity kickOpponent(@PathVariable String hostId) {
    lobbyService.kickOpponent(hostId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/lobby/my-game/{hostId}/status")
  public ResponseEntity startGame(@PathVariable String hostId) {
    lobbyService.startGame(hostId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/lobby/my-game/{userId}")
  public ResponseEntity leaveGame(@PathVariable String userId) {
    lobbyService.leaveLobby(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
