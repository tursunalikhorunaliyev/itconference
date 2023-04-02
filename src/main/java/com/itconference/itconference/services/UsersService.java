package com.itconference.itconference.services;

import com.itconference.itconference.entities.Users;
import com.itconference.itconference.model.PageableContentModel;
import com.itconference.itconference.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public ResponseEntity<PageableContentModel> userByPage(int page, int pageSize){


        List<Users> usersList = usersRepository.findAll(PageRequest.of(page-1, pageSize)).getContent();
        return ResponseEntity.ok(new PageableContentModel((long)usersList.size(), usersList));

    }
}
