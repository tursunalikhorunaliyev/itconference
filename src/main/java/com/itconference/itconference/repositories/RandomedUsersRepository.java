package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.RandomedUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RandomedUsersRepository extends JpaRepository<RandomedUsers, Long> {

}