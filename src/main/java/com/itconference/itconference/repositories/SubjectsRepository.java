package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectsRepository extends JpaRepository<Subjects, Long> {
}