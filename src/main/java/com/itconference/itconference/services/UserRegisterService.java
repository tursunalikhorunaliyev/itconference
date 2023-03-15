package com.itconference.itconference.services;

import com.itconference.itconference.entities.GeneratedCard;
import com.itconference.itconference.entities.Users;
import com.itconference.itconference.model.RegisterModel;
import com.itconference.itconference.model.RegisterSucces;
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


    public ResponseEntity<RegisterModel> register(String firstname, String lastname, String phone){
        final Users user = new Users();
        if(firstname != null && !firstname.isEmpty()){
            user.setFirstname(firstname.trim());
        }
        else {
            return ResponseEntity.ok(new RegisterModel(false, "Ism kiritilmagan"));
        }
        if(lastname != null && !lastname.isEmpty()){
            user.setLastname(lastname.trim());
        }
        else{
            return ResponseEntity.ok(new RegisterModel(false, "Familiya kiritilmagan"));
        }
        if(phone != null && !phone.isEmpty()){
            user.setPhone(phone);
        }
        else{
            return ResponseEntity.ok(new RegisterModel(false, "Telefon raqam kiritilmagan"));
        }

        if(usersRepository.existsByFirstname(firstname) && usersRepository.existsByLastname(lastname) && usersRepository.existsByPhone(phone)){
            return ResponseEntity.ok(new RegisterModel(false,"Ushbu foydalanuvchi ro'yxatdan o'tgan"));
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
        final RegisterSucces registerSucces = new RegisterSucces();
        registerSucces.setStatus(true);
        registerSucces.setMessage("Siz muvaffaqiyatli ro'yxatdan o'tdingiz");
        registerSucces.setFirstname(firstname);
        registerSucces.setLastname(lastname);
        registerSucces.setCardID(random_int);
        return ResponseEntity.ok(registerSucces);






    }

    public Long generate(){
        int min = 100000;
        int max = 999999;

        Long random_int =Long.parseLong( String.valueOf((int)Math.floor(Math.random() * (max - min + 1) + min)));
        boolean ifGeneratedExists = generatedRepository.existsByCardID(random_int);

        while (ifGeneratedExists){
            random_int = Long.parseLong( String.valueOf((int)Math.floor(Math.random() * (max - min + 1) + min)));
            ifGeneratedExists = generatedRepository.existsByCardID(random_int);
        }
        final GeneratedCard generatedCard = new GeneratedCard();
        generatedCard.setCardID(random_int);
        generatedRepository.save(generatedCard);
        return random_int;
    }
}
