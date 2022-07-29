package kg.itacademy.sewerfactory.entity;

import kg.itacademy.sewerfactory.enums.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    Roles roles;
}
