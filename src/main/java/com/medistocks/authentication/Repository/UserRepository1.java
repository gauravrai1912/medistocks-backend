package com.medistocks.authentication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medistocks.authentication.Entity.UserModel;



public interface UserRepository1 extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findById(Long id);

    void deleteById(Long id);

}
