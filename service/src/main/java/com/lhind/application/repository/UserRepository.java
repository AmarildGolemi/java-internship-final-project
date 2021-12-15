package com.lhind.application.repository;

import com.lhind.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * from user WHERE username = ?1", nativeQuery = true)
    Optional<User> findAllByUsername(String username);

}
