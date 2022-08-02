package kg.itacademy.sewerfactory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
}
