package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.RandomedUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomedUsersRepository extends JpaRepository<RandomedUsers, Long> {
}