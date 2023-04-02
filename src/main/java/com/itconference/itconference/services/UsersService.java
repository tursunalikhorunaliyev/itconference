package com.itconference.itconference.services;

import com.itconference.itconference.entities.Users;
import com.itconference.itconference.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public Page<Users> userByPage(int page){


        Pageable pageable = PageRequest.of(page, 14);
        return usersRepository.getUsersByPage(pageable);

    }
}
