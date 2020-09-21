package dawid.wiater.flightbase.application;

import dawid.wiater.flightbase.domain.flight.Flight;
import dawid.wiater.flightbase.domain.flight.FlightService;
import dawid.wiater.flightbase.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlightController {

  private static final String basePath = "/api/flights";

  private final FlightService service;
  private final UserService userService;

  @GetMapping(basePath + "/users")
  public ResponseEntity<List<Flight>> getAllForUser(@RequestParam(name = "userId") Long userId) {
    return ResponseEntity.ok(service.getAllForUser(userId));
  }

  @GetMapping(basePath)
  public ResponseEntity<List<Flight>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping(basePath + "/available")
  public ResponseEntity<List<Flight>> getAllAvailable(@RequestParam(name = "userId") Long userId) {
    return ResponseEntity.ok(service.getAllAvailable(userId));
  }

  @PostMapping(basePath)
  public ResponseEntity<Flight> create(
      @RequestBody Flight flight, @RequestParam("userId") Long userId) {
    if (userService.checkIfUserIsWorker(userId)) {
      service.create(flight);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @DeleteMapping(basePath + "/{flightId}")
  public ResponseEntity<Flight> remove(
      @PathVariable("flightId") Long flightId, @RequestParam("userId") Long userId) {
    if (userService.checkIfUserIsWorker(userId)) {
      service.remove(flightId);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @PostMapping(basePath + "/book")
  public ResponseEntity<Void> book(
      @RequestParam("userId") Long userId, @RequestParam("flightId") Long flightId) {
    service.book(userId, flightId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(basePath + "/cancel")
  public ResponseEntity<Void> cancel(
      @RequestParam("userId") Long userId, @RequestParam("flightId") Long flightId) {
    service.cancelForUser(userId, flightId);
    return ResponseEntity.noContent().build();
  }
}
