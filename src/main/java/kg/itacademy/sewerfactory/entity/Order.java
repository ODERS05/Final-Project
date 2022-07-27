package kg.itacademy.sewerfactory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity{
    @Column(name = "clothes_type", nullable = false)
    String clothesType;

    @Column(name = "amount", nullable = false)
    Long amount;

    @Column(name = "unit_price", nullable = false)
    Integer unitPrice;

    @Column(name = "status", nullable = false)
    String status;
}
