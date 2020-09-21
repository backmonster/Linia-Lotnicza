package dawid.wiater.flightbase.domain.flight;

import dawid.wiater.flightbase.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

  List<Flight> getAllByUsers(User user);
}
