package com.itconference.itconference.controllers;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.services.UserLoginService;
import com.itconference.itconference.services.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("it-conference-kuva")
@AllArgsConstructor
public class UsersController {
    private final UserRegisterService userRegisterService;
    private final UserLoginService userLoginService;
    @PostMapping("register")
    public ResponseEntity<ResultModel> register(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("phone") String phone){
        return userRegisterService.register(firstname, lastname, phone);
    }
    @PostMapping("login")
    public ResponseEntity<ResultModel> login(@RequestParam("phone") String phone){
        return userLoginService.login(phone);
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
