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

  @GetMapping("/lobby/games/{userId}")
  public ResponseEntity getGameList(@PathVariable String userId) {
    return new ResponseEntity<>(lobbyService.getPublicGames(userId), HttpStatus.OK);
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

  @GetMapping("/lobby/my-game/{userId}")
  public ResponseEntity getGameDetails(@PathVariable String userId) {
    return new ResponseEntity<>(lobbyService.getGameDetails(userId), HttpStatus.OK);
  }

  @PutMapping("/lobby/my-game/{hostId}/opponent")
  public ResponseEntity changeSides(@PathVariable String hostId) {
    lobbyService.changeSides(hostId);
    return new ResponseEntity<>(HttpStatus.OK);
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
