package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.DeletedUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeletedUsersRepository extends JpaRepository<DeletedUsers, Long> {
    Optional<DeletedUsers> findByPhone(String phone);
}