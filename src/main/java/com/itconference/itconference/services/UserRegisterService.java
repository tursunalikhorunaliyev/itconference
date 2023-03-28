package com.itconference.itconference.services;

import com.itconference.itconference.entities.GeneratedCard;
import com.itconference.itconference.entities.Users;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.model.ResultSucces;
import com.itconference.itconference.repositories.GeneratedRepository;
import com.itconference.itconference.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserRegisterService {
    private final UsersRepository usersRepository;
    private final GeneratedRepository generatedRepository;


    public ResponseEntity<ResultModel> register(String firstname, String lastname, String phone){
        final String firstnameOriginal = firstname.trim();
        final String lastnameOriginal = lastname.trim();
        final String phoneOriginal = phone.trim();

        final Users user = new Users();
        if(!firstnameOriginal.isEmpty()){
            user.setFirstname(firstname.trim());
        }
        else {
            return ResponseEntity.ok(new ResultModel(false, "Ism kiritilmagan"));
        }
        if(!lastnameOriginal.isEmpty()){
            user.setLastname(lastname.trim());
        }
        else{
            return ResponseEntity.ok(new ResultModel(false, "Familiya kiritilmagan"));
        }
        if(!phoneOriginal.isEmpty()){
            if(phoneOriginal.length()<13 || !phoneOriginal.startsWith("+998")){
                return ResponseEntity.ok(new ResultModel(false, "Telefon raqam xato kiritilgan"));
            }
            else{
                user.setPhone(phone);
            }
        }
        else{
            return ResponseEntity.ok(new ResultModel(false, "Telefon raqam kiritilmagan"));
        }
        if(usersRepository.existsByPhone(phoneOriginal)){
            return ResponseEntity.ok(new ResultModel(false, "Ushbu foydalanuvchi ro'yxatdan o'tgan"));
        }
        else if(usersRepository.existsByFirstname(firstnameOriginal) && usersRepository.existsByLastname(lastnameOriginal)){
            return ResponseEntity.ok(new ResultModel(false, "Ushbu foydalanuvchi ro'yxatdan o'tgan"));
        }
        int min = 100000;
        int max = 999999;

        Long random_int =Long.parseLong( String.valueOf((int)Math.floor(Math.random() * (max - min + 1) + min)));
        boolean ifGeneratedExists = generatedRepository.existsByCardID(random_int);

        while (ifGeneratedExists){
            random_int = Long.parseLong( String.valueOf((int)Math.floor(Math.random() * (max - min + 1) + min)));
            ifGeneratedExists = generatedRepository.existsByCardID(random_int);
        }

        final GeneratedCard generated = new GeneratedCard();
        generated.setCardID(random_int);
        user.setGenerated(generated);
        usersRepository.save(user);
        final ResultSucces registerSucces = new ResultSucces();
        registerSucces.setStatus(true);
        registerSucces.setMessage("Siz muvaffaqiyatli ro'yxatdan o'tdingiz");
        registerSucces.setFirstname(firstnameOriginal);
        registerSucces.setLastname(lastnameOriginal);
        registerSucces.setCardID(random_int);
        return ResponseEntity.ok(registerSucces);
    }

}
