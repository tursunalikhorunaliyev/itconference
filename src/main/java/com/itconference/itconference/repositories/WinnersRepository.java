package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.Users;
import com.itconference.itconference.entities.Winners;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WinnersRepository extends JpaRepository<Winners, Long> {
    Optional<Winners> findByUsers(Users users);

}