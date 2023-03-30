package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.GeneratedCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;


@Repository
public interface GeneratedCardRepository extends JpaRepository<GeneratedCard, Long> {
    boolean existsByCardID(Long cardID);


}