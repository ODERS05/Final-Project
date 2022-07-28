package kg.itacademy.sewerfactory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sewer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sewer{
    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "create_time", nullable = false)
    LocalDateTime createTime;

    @Column(name = "update_time")
    LocalDateTime updateTime;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

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
