package com.itconference.itconference.services;

import com.itconference.itconference.entities.Users;
import com.itconference.itconference.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public List<Users> userByPage(Long page){

        page--;
        Long offset = page*14;
        return usersRepository.getUsersByPage(offset);

    }
}
