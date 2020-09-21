package dawid.wiater.flightbase.domain.flight;

import dawid.wiater.flightbase.domain.user.User;
import dawid.wiater.flightbase.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

  private final FlightRepository repository;
  private final UserRepository userRepository;

  public List<Flight> getAllForUser(Long userId) {
    Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
      return repository.getAllByUsers(user.get());
    }
    return Collections.emptyList();
  }

  public List<Flight> getAll() {
    return repository.findAll();
  }

  public List<Flight> getAllAvailable(Long userId) {
    return repository.findAll().stream()
        .filter(flight -> flight.getFreePlaces() > 0)
        .filter(flight -> !flight.getUsers().contains(userRepository.getOne(userId)))
        .collect(Collectors.toList());
  }

  public Flight create(Flight flight) {
    return repository.save(flight);
  }

  @Transactional
  public void remove(Long flightId) {
    repository.findById(flightId).ifPresent(repository::delete);
  }

  @Transactional
  public void book(Long userId, Long flightId) {
    Optional<Flight> optionalFlight = repository.findById(flightId);
    optionalFlight.ifPresent(
        flight -> {
          User user = userRepository.getOne(userId);
          List<User> actualUsers = flight.getUsers();
          actualUsers.add(user);
          flight.setUsers(actualUsers);
        });
  }

  @Transactional
  public void cancelForUser(Long userId, Long flightId) {
    Optional<Flight> optionalFlight = repository.findById(flightId);
    optionalFlight.ifPresent(
            flight -> {
              User user = userRepository.getOne(userId);
              List<User> actualUsers = flight.getUsers();
              actualUsers.remove(user);
              flight.setUsers(actualUsers);
            });
  }
}
