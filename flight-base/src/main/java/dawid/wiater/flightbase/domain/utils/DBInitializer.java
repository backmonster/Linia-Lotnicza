package dawid.wiater.flightbase.domain.utils;

import dawid.wiater.flightbase.domain.flight.Flight;
import dawid.wiater.flightbase.domain.flight.FlightService;
import dawid.wiater.flightbase.domain.user.Role;
import dawid.wiater.flightbase.domain.user.User;
import dawid.wiater.flightbase.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DBInitializer {

  private final UserService userService;
  private final FlightService flightService;

  @EventListener(ApplicationReadyEvent.class)
  public void fillDb() {
    this.createUsers();
  }

  private void createUsers() {
    User client = new User();
    client.setRoleName(Role.CLIENT);
    client.setFirstName("Jan");
    client.setLastName("Nowak");
    client.setUsername("client");
    client.setPassword("client");
    userService.register(client);

    User client2 = new User();
    client2.setRoleName(Role.CLIENT);
    client2.setFirstName("Jan");
    client2.setLastName("Nowak");
    client2.setUsername("client2");
    client2.setPassword("client");
    userService.register(client2);

    User client3 = new User();
    client3.setRoleName(Role.CLIENT);
    client3.setFirstName("Jan");
    client3.setLastName("Nowak");
    client3.setUsername("client3");
    client3.setPassword("client");
    userService.register(client3);

    User worker = new User();
    worker.setRoleName(Role.WORKER);
    worker.setFirstName("Marcin");
    worker.setLastName("Kowalski");
    worker.setUsername("admin");
    worker.setPassword("admin");
    userService.register(worker);

    Flight flight1 = new Flight();
    flight1.setDate(LocalDate.now().plusDays(2));
    flight1.setSeatsOnPlane(25);
    flight1.setDestinationCity("Rzeszow");
    flight1.setSourceCity("Warszawa");
    flight1.setUsers(Arrays.asList(client, client2, client3));
    flightService.create(flight1);

    Flight flight2 = new Flight();
    flight2.setDate(LocalDate.now().plusDays(20));
    flight2.setSeatsOnPlane(5);
    flight2.setDestinationCity("Warszawa");
    flight2.setSourceCity("Gdansk");
    flight2.setUsers(Arrays.asList(client, client3));
    flightService.create(flight2);
  }
}
