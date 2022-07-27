package kg.itacademy.sewerfactory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "sewer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sewer extends BaseEntity{
    @Column(name = "fio", nullable = false, unique = true)
    String fio;

    @Column(name = "need_amount")
    Long needAmount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @Column(name = "status", nullable = false)
    String status;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    Department department;
}
