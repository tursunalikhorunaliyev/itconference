package com.itconference.itconference.controllers;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.services.UserLoginService;
import com.itconference.itconference.services.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("it-conference-kuva/auth")
@AllArgsConstructor
public class AuthController {
    private final UserRegisterService userRegisterService;
    private final UserLoginService userLoginService;


    @PostMapping("register")
    public ResponseEntity<ResultModel> register(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("phone") String phone,
            @RequestParam(required = false ,value = "device") String os){

        return userRegisterService.register(firstname, lastname, phone, os);

    }
    @PostMapping("login")
    public ResponseEntity<ResultModel> login(@RequestParam("phone") String phone){
        return userLoginService.login(phone);
    }


}
