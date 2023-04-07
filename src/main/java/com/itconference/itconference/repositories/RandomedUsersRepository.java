package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.RandomedUsers;
import com.itconference.itconference.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RandomedUsersRepository extends JpaRepository<RandomedUsers, Long> {
    Optional<RandomedUsers> findByUsers(Users users);

}