package ro.vladrochian.projects.ttt.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.vladrochian.projects.ttt.server.exception.GameNotFoundException;
import ro.vladrochian.projects.ttt.server.exception.GameNotStartedException;
import ro.vladrochian.projects.ttt.server.exception.IllegalMoveException;
import ro.vladrochian.projects.ttt.server.exception.NotYourTurnException;
import ro.vladrochian.projects.ttt.server.model.ActiveGameApi;
import ro.vladrochian.projects.ttt.server.model.MoveApi;
import ro.vladrochian.projects.ttt.server.model.OnlineGame;
import ro.vladrochian.projects.ttt.server.model.TableStateApi;
import ro.vladrochian.projects.ttt.server.session.GameRepository;
import ro.vladrochian.projects.ttt.server.session.UserRepository;
import ro.vladrochian.projects.ttt.table.Position;
import ro.vladrochian.projects.ttt.table.Table;

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
    String[] players = game.getPlayers();
    String[] playerNames = new String[2];
    playerNames[0] = (players[0] == null) ? "Computer" : userRepository.findById(players[0]).getName();
    playerNames[1] = (players[1] == null) ? "Computer" : userRepository.findById(players[1]).getName();
    activeGame.setPlayerNames(playerNames);
    Table table = game.getTable();
    long state = game.getState();
    activeGame.setTableState(new TableStateApi(table, state));
    if (table.isGameFinished(state)) {
      if (table.getWinner(state) == 0) {
        activeGame.setGameStatus("DRAW");
      } else {
        activeGame.setGameStatus("LOSE");
      }
    } else {
      activeGame.setGameStatus("NOT_FINISHED");
    }
    return activeGame;
  }

  public void leaveGame(String userId) {
    getGameForUser(userId);
    gameRepository.deleteByUserId(userId);
  }

  public boolean hasOpponentMoved(String userId) {
    OnlineGame game = getGameForUser(userId);
    int currentPlayer = game.getTable().getCurrentPlayer(game.getState());
    return userId.equals(game.getPlayers()[currentPlayer - 1]);
  }

  private void returnToLobby(OnlineGame game) {
    game.setState(game.getTable().initialState());
    game.end();
  }

  private void performBotMove(OnlineGame game) {
    Table table = game.getTable();
    long state = game.getState();
    if (table.isGameFinished(state)) {
      returnToLobby(game);
    } else {
      game.setState(table.getNextState(state, game.getBotAlgorithm().getMove(state)));
    }
  }

  public String performMove(String userId, MoveApi move) {
    if (!hasOpponentMoved(userId)) {
      throw new NotYourTurnException();
    }
    OnlineGame game = getGameForUser(userId);
    Table table = game.getTable();
    long state = game.getState();
    if (table.isGameFinished(state)) {
      returnToLobby(game);
      return "";
    }
    Position p = Position.from(move.getRow(), move.getColumn());
    if (!table.getAvailableMoves(state).contains(p)) {
      throw new IllegalMoveException();
    }
    state = table.getNextState(state, p);
    game.setState(state);
    String ans;
    String[] players = game.getPlayers();
    boolean isBot = (players[0] == null || players[1] == null);
    if (table.isGameFinished(state)) {
      if (table.getWinner(state) == 0) {
        ans = "DRAW";
      } else {
        ans = "WIN";
      }
    } else {
      ans = "NOT_FINISHED";
    }
    if (isBot) {
      performBotMove(game);
    }
    return ans;
  }
}
