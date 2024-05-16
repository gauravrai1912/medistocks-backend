package com.medistocks.authentication.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID userId);

    Optional<User> findByEmployeeId(int employeeId);


    void deleteById(UUID userId);
}
