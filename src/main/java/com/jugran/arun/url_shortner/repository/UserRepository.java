package com.jugran.arun.url_shortner.repository;

import com.jugran.arun.url_shortner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByApiDevKey(String apiDevKey);

    Optional<User> findByEmail(String email);

    boolean existsByApiDevKey(String apiDevKey);
}

