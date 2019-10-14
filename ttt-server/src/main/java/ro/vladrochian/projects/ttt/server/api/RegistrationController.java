package ro.vladrochian.projects.ttt.server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/registration")
public class RegistrationController {
  @PostMapping("/player")
  public ResponseEntity createPlayer(@RequestBody String name) {
    return null;
  }
}
