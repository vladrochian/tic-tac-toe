package ro.vladrochian.projects.ttt.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.vladrochian.projects.ttt.server.exception.*;
import ro.vladrochian.projects.ttt.server.model.CreateGameApi;
import ro.vladrochian.projects.ttt.server.model.GameSummaryApi;
import ro.vladrochian.projects.ttt.server.model.OnlineGame;
import ro.vladrochian.projects.ttt.server.session.GameRepository;
import ro.vladrochian.projects.ttt.server.session.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LobbyService {
  private GameRepository gameRepository;
  private UserRepository userRepository;

  @Autowired
  public LobbyService(GameRepository gameRepository, UserRepository userRepository) {
    this.gameRepository = gameRepository;
    this.userRepository = userRepository;
  }

  public String createGame(CreateGameApi gameData) {
    if (gameRepository.findByUserId(gameData.getHostId()) != null) {
      throw new AlreadyInGameException();
    }
    return gameRepository.add(gameData).getCode();
  }

  public List<GameSummaryApi> getPublicGames() {
    List<OnlineGame> games = gameRepository.findAllPublicAndNotStartedAndOpen();
    List<GameSummaryApi> ans = new ArrayList<>();
    for (OnlineGame game : games) {
      GameSummaryApi gameApi = new GameSummaryApi();
      gameApi.setCode(game.getCode());
      gameApi.setHostName(userRepository.findById(game.getHostId()).getName());
      gameApi.setTableHeight(game.getTableHeight());
      gameApi.setTableWidth(game.getTableWidth());
      gameApi.setLineSize(game.getLineSize());
      ans.add(gameApi);
    }
    return ans;
  }

  private void handleStartedGame(OnlineGame game) {
    if (game.isStarted()) {
      throw new GameStartedException();
    }
  }

  public void joinGame(String gameCode, String userId) {
    if (gameRepository.findByUserId(userId) != null) {
      throw new AlreadyInGameException();
    }
    OnlineGame game = gameRepository.findByCode(gameCode);
    if (game == null) {
      throw new GameNotFoundException();
    }
    handleStartedGame(game);
    String[] players = game.getPlayers();
    if (players[0] == null) {
      players[0] = userId;
    } else if (players[1] == null) {
      players[1] = userId;
    } else {
      throw new PartyFullException();
    }
  }

  public void kickOpponent(String hostId) {
    OnlineGame game = gameRepository.findByHostId(hostId);
    if (game == null) {
      throw new UserNotHostException();
    }
    handleStartedGame(game);
    String[] players = game.getPlayers();
    if (hostId.equals(players[0])) {
      players[1] = null;
    } else {
      players[0] = null;
    }
  }

  public void leaveLobby(String userId) {
    OnlineGame game = gameRepository.findByUserId(userId);
    if (game == null) {
      throw new UserNotInGameException();
    }
    handleStartedGame(game);
    if (userId.equals(game.getHostId())) {
      gameRepository.deleteByHostId(userId);
    } else {
      String[] players = game.getPlayers();
      if (userId.equals(players[0])) {
        players[0] = null;
      } else {
        players[1] = null;
      }
    }
  }

  public void startGame(String hostId) {
    OnlineGame game = gameRepository.findByHostId(hostId);
    if (game == null) {
      throw new UserNotHostException();
    }
    handleStartedGame(game);
    game.start();
  }
}
