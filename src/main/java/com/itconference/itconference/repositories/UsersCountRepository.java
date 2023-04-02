package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.UsersCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersCountRepository extends JpaRepository<UsersCount, Long> {
}