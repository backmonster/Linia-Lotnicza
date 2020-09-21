package dawid.wiater.flightbase.domain.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import dawid.wiater.flightbase.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Flight {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String sourceCity;
  private String destinationCity;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @NotNull
  private LocalDate date;

  @NotNull private Integer seatsOnPlane;

  @ManyToMany private List<User> users;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  public Integer getFreePlaces() {
    return seatsOnPlane - users.size();
  }
}
