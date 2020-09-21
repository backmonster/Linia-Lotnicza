package dawid.wiater.flightbase.domain.user;

import com.sun.istack.NotNull;
import dawid.wiater.flightbase.domain.flight.Flight;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull private String username;
  @NotNull private String password;
  private String firstName;
  private String lastName;
  private Role roleName;
  private Boolean isBlocked = false;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Flight> flights;
}
