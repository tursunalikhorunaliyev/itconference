package com.itconference.itconference.controllers;

import com.itconference.itconference.entities.Users;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.repositories.UsersCountRepository;
import com.itconference.itconference.repositories.UsersRepository;
import com.itconference.itconference.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("it-conference-kuva/users")
@AllArgsConstructor
public class UsersController {
    private final UsersRepository usersRepository;
    private final UsersService usersService;

    @GetMapping("all")
    public ResponseEntity<List<Users>> allv1Users(){
        return ResponseEntity.ok(usersRepository.findAll());
    }
    @GetMapping("paging")
    public ResponseEntity<ResultModel> users(@RequestParam int page, @RequestParam int pageSize){
        return usersService.userByPage(page, pageSize);
    }
    @GetMapping("today")
    public ResponseEntity<List<Users>> today(){
        return usersService.today();
    }
    @GetMapping("yesterday")
    public ResponseEntity<List<Users>> yesterday(){
        return usersService.yesterday();

    }
    @PostMapping("delete")
    public ResponseEntity<ResultModel> saveUser(@RequestParam("id") Long id){
        return usersService.deleteUser(id);
    }

    @GetMapping("test-server")
    public String test(){
        return "Server running.....";
    }

    @GetMapping("/")
    public String getPage(){
        return "index";
    }
}
