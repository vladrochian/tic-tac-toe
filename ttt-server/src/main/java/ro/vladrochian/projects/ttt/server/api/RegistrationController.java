package ro.vladrochian.projects.ttt.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.vladrochian.projects.ttt.server.service.RegistrationService;

@RestController("/registration")
public class RegistrationController {
  private RegistrationService registrationService;

  @Autowired
  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @PostMapping("/players")
  public ResponseEntity registerPlayer(@RequestBody String name) {
    return new ResponseEntity<>(registrationService.registerUser(name), HttpStatus.CREATED);
  }
}
