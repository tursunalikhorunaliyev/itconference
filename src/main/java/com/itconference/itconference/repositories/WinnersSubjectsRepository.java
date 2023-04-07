package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.Subjects;
import com.itconference.itconference.entities.WinnersSubjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface WinnersSubjectsRepository extends JpaRepository<WinnersSubjects, Long> {
    Optional<WinnersSubjects> findBySubjects(Subjects subjects);
}