package ro.vladrochian.projects.ttt.server.session;

import org.springframework.stereotype.Component;
import ro.vladrochian.projects.ttt.server.model.CreateGameApi;
import ro.vladrochian.projects.ttt.server.model.OnlineGame;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameRepository {
  private List<OnlineGame> games;

  public GameRepository() {
    games = new ArrayList<>();
  }

  public OnlineGame add(CreateGameApi gameData) {
    OnlineGame game = new OnlineGame(gameData);
    games.add(game);
    return game;
  }

  public List<OnlineGame> findAllPublicAndNotStartedAndOpen() {
    List<OnlineGame> ans = new ArrayList<>();
    for (OnlineGame game : games) {
      if (game.isGamePublic() && !game.isStarted() && game.isOpen()) {
        ans.add(game);
      }
    }
    return ans;
  }

  public OnlineGame findByCode(String code) {
    for (OnlineGame game : games) {
      if (game.getCode().equals(code)) {
        return game;
      }
    }
    return null;
  }

  public OnlineGame findByHostId(String hostId) {
    for (OnlineGame game : games) {
      if (game.getHostId().equals(hostId)) {
        return game;
      }
    }
    return null;
  }

  public OnlineGame findByUserId(String userId) {
    for (OnlineGame game : games) {
      String[] players = game.getPlayers();
      if (userId.equals(players[0]) || userId.equals(players[1])) {
        return game;
      }
    }
    return null;
  }

  public void deleteByHostId(String hostId) {
    games.remove(findByHostId(hostId));
  }

  public void deleteByUserId(String userId) {
    games.remove(findByUserId(userId));
  }
}
