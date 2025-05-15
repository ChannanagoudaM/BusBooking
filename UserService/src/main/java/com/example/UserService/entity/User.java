package com.example.UserService.entity;

import com.example.UserService.constants.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "users", indexes = {@Index(name = "idx_user_email", columnList = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;

//    @Pattern(
//            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,12}$",
//            message = "Password must be 8–12 characters, with 1 uppercase letter, 1 digit, and 1 special character")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @Column(length = 10)
    @Size(min = 10, max = 10,message = "Phone number must be exactly 10 digits")
    private String phone;
}

//@BatchSize= no of entities to be fetched in a single query
//@BatchSize(size = 50)
//class User
//{
//
//}
