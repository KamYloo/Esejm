package pb.wi.mmw.e_sejm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "role")
@Table(name = "role")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
