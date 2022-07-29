package kg.itacademy.sewerfactory.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}