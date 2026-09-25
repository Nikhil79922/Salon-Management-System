package com.example.user_service.model;


import com.example.user_service.model.enums.UserRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;

    private String email;

    private String username;

    private String phone;

    private UserRoles role;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Users(String fullName, String email, String username, String phone, UserRoles role, LocalDateTime createAt, LocalDateTime updateAt) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.role = role;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
