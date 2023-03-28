package com.itconference.itconference.services;

import com.itconference.itconference.entities.RandomedUsers;
import com.itconference.itconference.entities.Users;
import com.itconference.itconference.repositories.RandomedUsersRepository;
import com.itconference.itconference.repositories.UsersRepository;
import com.itconference.itconference.repositories.WinnersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RandomService {
    private final RandomedUsersRepository randomedUsersRepository;
    private final WinnersRepository winnersRepository;

    private final UsersRepository usersRepository;


    private List<Users> usersList;



    public void startRandom(){
        usersList = usersRepository.findAll();
    }
}
