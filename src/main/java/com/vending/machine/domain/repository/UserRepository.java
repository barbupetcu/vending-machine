package com.vending.machine.domain.repository;

import com.vending.machine.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u join fetch u.roles where u.id = :userId")
    Optional<User> findByIdWithRoles(@Param("userId") Long userId);
}
