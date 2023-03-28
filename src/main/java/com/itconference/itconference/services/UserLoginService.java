package com.itconference.itconference.services;
import com.itconference.itconference.entities.Users;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.model.ResultSucces;
import com.itconference.itconference.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserLoginService {

    private UsersRepository usersRepository;

    public ResponseEntity<ResultModel> login(String phone){
        final String phoneOriginal = phone.trim();
        if(phoneOriginal.trim().length()<13 && !phoneOriginal.startsWith("+998")){
            return ResponseEntity.ok(new ResultModel(false, "raqam noto'g'ri kiritilgan"));
        }
        else{
            Optional<Users> usersOptional = usersRepository.findByPhone(phoneOriginal);
            if(usersOptional.isPresent()){
                Users user = usersOptional.get();
                return ResponseEntity.ok(new ResultSucces(user.getFirstname(), user.getLastname(), user.getGenerated().getCardID()));
            }
            else{
                return ResponseEntity.ok(new ResultModel(false, "Bu foydalanuvchi tizimda ro'yxatdan o'tkazilmagan"));
            }
        }
    }
}
