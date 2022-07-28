package kg.itacademy.sewerfactory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "department")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department extends BaseEntity{
    @Column(name = "department_name", nullable = false, unique = true)
    String departmentName;

    @ManyToMany
    @JoinColumn(name = "sewer_id")
    List<Sewer> sewers;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
}
