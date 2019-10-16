package ro.vladrochian.projects.ttt.server.session;

import org.springframework.stereotype.Component;
import ro.vladrochian.projects.ttt.server.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {
  private List<User> users;

  public UserRepository() {
    users = new ArrayList<>();
  }

  public User add(String name) {
    User user = new User(name);
    users.add(user);
    return user;
  }

  public User findById(String id) {
    for (User user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    return null;
  }
}
