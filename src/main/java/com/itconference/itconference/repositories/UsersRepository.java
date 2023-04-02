package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.GeneratedCard;
import com.itconference.itconference.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByPhone(String phone);
    boolean existsByLastname(String lastname);
    boolean existsByFirstname(String firstname);
    Optional<Users> findByPhone(String phone);
    Optional<Users> findByGenerated(GeneratedCard generatedCard);

    @Query(value = "select * from users order by id", nativeQuery = true)
    Page<Users> getUsersByPage(Pageable pageable);

}