package ro.vladrochian.projects.ttt.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.vladrochian.projects.ttt.server.exception.*;
import ro.vladrochian.projects.ttt.server.model.CreateGameApi;
import ro.vladrochian.projects.ttt.server.model.GameSummaryApi;
import ro.vladrochian.projects.ttt.server.model.OnlineGame;
import ro.vladrochian.projects.ttt.server.session.GameRepository;
import ro.vladrochian.projects.ttt.server.session.UserRepository;
import ro.vladrochian.projects.ttt.table.Table;

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

  private void checkUser(String userId) {
    if (userRepository.findById(userId) == null) {
      throw new UserNotFoundException();
    }
  }

  public String createGame(CreateGameApi gameData) {
    checkUser(gameData.getHostId());
    if (gameRepository.findByUserId(gameData.getHostId()) != null) {
      throw new AlreadyInGameException();
    }
    return gameRepository.add(gameData).getCode();
  }

  private String playerName(String id) {
    if (id == null) return null;
    checkUser(id);
    return userRepository.findById(id).getName();
  }

  private GameSummaryApi summary(OnlineGame game) {
    GameSummaryApi gameApi = new GameSummaryApi();
    gameApi.setCode(game.getCode());
    gameApi.setHostName(playerName(game.getHostId()));
    gameApi.setTableHeight(game.getTableHeight());
    gameApi.setTableWidth(game.getTableWidth());
    gameApi.setLineSize(game.getLineSize());
    String[] playerNames = new String[2];
    playerNames[0] = playerName(game.getPlayers()[0]);
    playerNames[1] = playerName(game.getPlayers()[1]);
    gameApi.setPlayerNames(playerNames);
    return gameApi;
  }

  public List<GameSummaryApi> getPublicGames(String userId) {
    checkUser(userId);
    List<OnlineGame> games = gameRepository.findAllPublicAndNotStartedAndOpen();
    List<GameSummaryApi> ans = new ArrayList<>();
    for (OnlineGame game : games) {
      ans.add(summary(game));
    }
    return ans;
  }

  private void handleStartedGame(OnlineGame game) {
    if (game.isStarted()) {
      throw new GameStartedException();
    }
  }

  public GameSummaryApi getGameDetails(String userId) {
    checkUser(userId);
    OnlineGame game = gameRepository.findByUserId(userId);
    if (game == null) {
      throw new GameNotFoundException();
    }
    GameSummaryApi gameApi = summary(game);
    gameApi.setMyGame(userId.equals(game.getHostId()));
    gameApi.setStarted(game.isStarted());
    return gameApi;
  }

  public void joinGame(String gameCode, String userId) {
    checkUser(userId);
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

  public void changeSides(String hostId) {
    checkUser(hostId);
    OnlineGame game = gameRepository.findByHostId(hostId);
    if (game == null) {
      throw new UserNotHostException();
    }
    handleStartedGame(game);
    String[] players = game.getPlayers();
    String aux = players[0];
    players[0] = players[1];
    players[1] = aux;
  }

  public void kickOpponent(String hostId) {
    checkUser(hostId);
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
    checkUser(userId);
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
    checkUser(hostId);
    OnlineGame game = gameRepository.findByHostId(hostId);
    if (game == null) {
      throw new UserNotHostException();
    }
    handleStartedGame(game);
    game.start();
    if (game.getPlayers()[0] == null) {
      Table table = game.getTable();
      long state = game.getState();
      game.setState(table.getNextState(state, game.getBotAlgorithm().getMove(state)));
    }
  }
}
