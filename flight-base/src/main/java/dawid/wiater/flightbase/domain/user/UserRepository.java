package dawid.wiater.flightbase.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> getByUsernameAndPassword(String username, String password);

  Optional<User> getByUsername(String username);

}
