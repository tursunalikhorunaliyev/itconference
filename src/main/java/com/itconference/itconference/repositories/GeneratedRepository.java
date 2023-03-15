package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.GeneratedCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneratedRepository extends JpaRepository<GeneratedCard, Long> {
    boolean existsByCardID(Long cardID);
}