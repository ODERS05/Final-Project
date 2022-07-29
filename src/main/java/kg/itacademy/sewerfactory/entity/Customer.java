package kg.itacademy.sewerfactory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
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
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "fio", nullable = false, unique = true)
    String fio;

    @Column(name = "phone_number", nullable = false, unique = true)
    String phoneNumber;
}
