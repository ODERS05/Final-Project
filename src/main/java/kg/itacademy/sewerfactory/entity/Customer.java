package kg.itacademy.sewerfactory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends BaseEntity{
    @Column(name = "fio", nullable = false, unique = true)
    String fio;

    @ManyToMany
    @JoinColumn(name = "order_id")
    List<Order> orders;
}
