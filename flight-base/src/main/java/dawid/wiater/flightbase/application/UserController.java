package dawid.wiater.flightbase.application;

import dawid.wiater.flightbase.domain.user.User;
import dawid.wiater.flightbase.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

  private static final String basePath = "/api";

  private final UserService service;

  @PostMapping(basePath + "/login")
  public ResponseEntity<User> login(
      @RequestParam("username") String username, @RequestParam("password") String password) {
    if (!service.existsByUsernameAndPassword(username, password)) {
      return ResponseEntity.notFound().build();
    }
    if (service.checkIfYourAccountIsActive(username, password)) {
      return ResponseEntity.unprocessableEntity().build();
    }
    return ResponseEntity.ok(service.login(username, password));
  }

  @PostMapping(basePath + "/register")
  public ResponseEntity<User> register(@RequestBody User user) {
    if (service.existsByUsername(user.getUsername())) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(service.register(user));
  }

  @GetMapping(basePath + "/users")
  public ResponseEntity<List<User>> getAll(@RequestParam("userId") Long userId) {
    if (service.checkIfUserIsWorker(userId)) {
      return ResponseEntity.ok(service.getAllClients());
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @GetMapping(basePath + "/users/{userId}")
  public ResponseEntity<User> getOne(@PathVariable("userId") Long userId) {
    return ResponseEntity.ok(service.getOne(userId));
  }

  @PatchMapping(basePath + "/users/block")
  public ResponseEntity<Void> block(
      @RequestParam("userId") Long userId, @RequestParam("blockedUserId") Long blockedUserId) {
    if (service.checkIfUserIsWorker(userId)) {
      service.block(blockedUserId);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
