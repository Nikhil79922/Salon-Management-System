package com.example.user_service.model;


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

    private String password;

    private String username;

    private String phone;

    private String role;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Users(String fullName, String email, String password, String username, String phone, String role, LocalDateTime createAt, LocalDateTime updateAt) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.role = role;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
