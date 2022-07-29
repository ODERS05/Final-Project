package kg.itacademy.sewerfactory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.swing.text.StyledEditorKit;

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
    String status;

    @Column(name = "new_order", nullable = false)
    Boolean newOrder;
}
