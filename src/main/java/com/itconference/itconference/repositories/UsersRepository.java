package com.itconference.itconference.repositories;

import com.itconference.itconference.entities.GeneratedCard;
import com.itconference.itconference.entities.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByPhone(String phone);
    boolean existsByLastname(String lastname);
    boolean existsByFirstname(String firstname);
    Optional<Users> findByPhone(String phone);
    Optional<Users> findByGenerated(GeneratedCard generatedCard);

    @Query(value = "SELECT id FROM users where DATE(date) = current_date()", nativeQuery = true)
    List<Long> today();

    @Query(value = "SELECT id FROM users where DATE(date) = current_date()-1", nativeQuery = true)
    List<Long> yesterday();

    List<Users> findByIdIn(Collection<Long> ids);

}