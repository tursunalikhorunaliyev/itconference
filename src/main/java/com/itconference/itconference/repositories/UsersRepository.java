package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByPhone(String phone);
    boolean existsByLastname(String lastname);
    boolean existsByFirstname(String firstname);
    Optional<Users> findByPhone(String phone);
}