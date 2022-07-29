package kg.itacademy.sewerfactory.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{
    @Column(name = "user_name",nullable = false,unique = true)
    String login;

    @Email
    @Column(name = "email",nullable = false,unique = true)
    String email;

    @Column(name = "password",nullable = false)
    String password;

    @Column(name = "is_active")
    Boolean isActive;


}
