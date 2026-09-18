package com.example.user_service.repository;

import com.example.user_service.model.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(@NotBlank(message = "Username is required") @Size(
            min = 2,
            max = 30,
            message = "Username must be between 2 and 30 characters"
    ) @Pattern(
            regexp = "^@[a-zA-Z0-9_]+$",
            message = "Username must start with @ and contain only letters, numbers and underscores"
    ) String username);

}
