package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.Winners;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnersRepository extends JpaRepository<Winners, Long> {
}