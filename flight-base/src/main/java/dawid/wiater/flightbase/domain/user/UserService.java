package dawid.wiater.flightbase.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  public boolean existsByUsername(String username) {
    return repository.getByUsername(username).isPresent();
  }

  public boolean existsByUsernameAndPassword(String username, String password) {
    return repository.getByUsernameAndPassword(username, password).isPresent();
  }

  public boolean checkIfYourAccountIsActive(String username, String password) {
    return repository
        .getByUsernameAndPassword(username, password)
        .filter(User::getIsBlocked)
        .isPresent();
  }

  public User login(String username, String password) {
    return repository
        .getByUsernameAndPassword(username, password)
        .orElseThrow(() -> new RuntimeException("User with these credentials does not exist"));
  }

  public User register(User user) {
    if (repository.getByUsername(user.getUsername()).isPresent()) {
      throw new IllegalArgumentException("User with these username already exists");
    }
    if (user.getRoleName() == null) {
      user.setRoleName(Role.CLIENT);
    }
    return repository.save(user);
  }

  public List<User> getAllClients() {
    return repository.findAll().stream()
        .filter(user -> Role.CLIENT.equals(user.getRoleName()))
        .collect(Collectors.toList());
  }

  public User getOne(Long userId) {
    return repository.findById(userId).orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  public void block(Long blockedUserId) {
    repository.findById(blockedUserId).ifPresent(user -> user.setIsBlocked(true));
  }

  public boolean checkIfUserIsWorker(Long userId) {
    return repository
        .findById(userId)
        .filter(user -> user.getRoleName().equals(Role.WORKER))
        .isPresent();
  }
}
