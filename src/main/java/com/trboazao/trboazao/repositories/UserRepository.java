package com.trboazao.trboazao.repositories;

import com.trboazao.trboazao.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);

    @Query(value = "SELECT u.password FROM Users u WHERE u.email = :email AND u.password = :password")
    void login(@Param("email") String email, @Param("password") String password);
}
