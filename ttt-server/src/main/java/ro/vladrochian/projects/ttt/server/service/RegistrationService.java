package ro.vladrochian.projects.ttt.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.vladrochian.projects.ttt.server.session.UserRepository;

@Service
public class RegistrationService {
  private UserRepository userRepository;

  @Autowired
  public RegistrationService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public String registerUser(String name) {
    return userRepository.add(name).getId();
  }
}
