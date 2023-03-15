package com.itconference.itconference.controllers;

import com.itconference.itconference.model.RegisterModel;
import com.itconference.itconference.services.UserRegisterService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("it-conference-kuva")
@AllArgsConstructor
public class UsersController {

    private final UserRegisterService userRegisterService;
    @PostMapping("register")
    public ResponseEntity<RegisterModel> register(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("phone") String phone){
        return userRegisterService.register(firstname, lastname, phone);
    }

    @GetMapping("generate")
    public Long generateCardID(){
        return userRegisterService.generate();
    }

}
