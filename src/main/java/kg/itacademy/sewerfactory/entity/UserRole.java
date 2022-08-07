package kg.itacademy.sewerfactory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRole extends BaseEntity{
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

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;
}
