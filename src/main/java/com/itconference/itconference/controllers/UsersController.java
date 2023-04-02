package com.itconference.itconference.controllers;
import com.itconference.itconference.entities.Users;
import com.itconference.itconference.model.PageableContentModel;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.repositories.UsersRepository;
import com.itconference.itconference.services.UserLoginService;
import com.itconference.itconference.services.UserRegisterService;
import com.itconference.itconference.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("it-conference-kuva/auth")
@AllArgsConstructor
public class UsersController {
    private final UserRegisterService userRegisterService;
    private final UserLoginService userLoginService;
    private final UsersRepository usersRepository;
    private final UsersService usersService;

    @PostMapping("register")
    public ResponseEntity<ResultModel> register(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("phone") String phone, @RequestParam(required = false ,value = "device") String os){
        return userRegisterService.register(firstname, lastname, phone, os);
    }
    @PostMapping("login")
    public ResponseEntity<ResultModel> login(@RequestParam("phone") String phone){
        return userLoginService.login(phone);
    }

    @GetMapping("all-v1users")
    public ResponseEntity<List<Users>> allv1Users(){
        return ResponseEntity.ok(usersRepository.findAll());
    }

    @GetMapping("users")
    public ResponseEntity<PageableContentModel> users(@RequestParam int page, @RequestParam int pageSize){
        return usersService.userByPage(page, pageSize);
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
