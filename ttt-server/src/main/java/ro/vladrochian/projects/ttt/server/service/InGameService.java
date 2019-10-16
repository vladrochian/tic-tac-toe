package ro.vladrochian.projects.ttt.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.vladrochian.projects.ttt.server.exception.GameNotFoundException;
import ro.vladrochian.projects.ttt.server.exception.GameNotStartedException;
import ro.vladrochian.projects.ttt.server.model.ActiveGameApi;
import ro.vladrochian.projects.ttt.server.model.OnlineGame;
import ro.vladrochian.projects.ttt.server.model.TableStateApi;
import ro.vladrochian.projects.ttt.server.session.GameRepository;
import ro.vladrochian.projects.ttt.server.session.UserRepository;

@Service
public class InGameService {
  private GameRepository gameRepository;
  private UserRepository userRepository;

  @Autowired
  public InGameService(GameRepository gameRepository, UserRepository userRepository) {
    this.gameRepository = gameRepository;
    this.userRepository = userRepository;
  }

  private OnlineGame getGameForUser(String userId) {
    OnlineGame game = gameRepository.findByUserId(userId);
    if (game == null) {
      throw new GameNotFoundException();
    }
    if (!game.isStarted()) {
      throw new GameNotStartedException();
    }
    return game;
  }

  public ActiveGameApi getGame(String userId) {
    OnlineGame game = getGameForUser(userId);
    ActiveGameApi activeGame = new ActiveGameApi();
    activeGame.setScore(game.getScore());
    String[] players = game.getPlayers();
    String[] playerNames = new String[2];
    playerNames[0] = (players[0] == null) ? "Computer" : userRepository.findById(players[0]).getName();
    playerNames[1] = (players[1] == null) ? "Computer" : userRepository.findById(players[1]).getName();
    activeGame.setPlayerNames(playerNames);
    activeGame.setTableState(new TableStateApi(game.getTable(), game.getState()));
    return activeGame;
  }

  public void leaveGame(String userId) {
    getGameForUser(userId);
    gameRepository.deleteByUserId(userId);
  }
}
