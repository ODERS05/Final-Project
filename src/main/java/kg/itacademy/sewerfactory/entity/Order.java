package kg.itacademy.sewerfactory.entity;

import kg.itacademy.sewerfactory.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.swing.text.StyledEditorKit;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @Column(name = "clothes_type", nullable = false)
    String clothesType;

    @Column(name = "amount", nullable = false)
    Long amount;

    @Column(name = "unit_price", nullable = false)
    Integer unitPrice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "new_order", nullable = false)
    Boolean newOrder;

    @Column(name = "total_cost", nullable = false)
    BigDecimal totalCost;
}
