package com.itconference.itconference.services;

import com.itconference.itconference.entities.GeneratedCard;
import com.itconference.itconference.entities.Users;
import com.itconference.itconference.entities.UsersCount;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.model.ResultModelData;
import com.itconference.itconference.repositories.GeneratedCardRepository;
import com.itconference.itconference.repositories.UsersCountRepository;
import com.itconference.itconference.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserRegisterService {
    private final UsersRepository usersRepository;
    private final GeneratedCardRepository generatedRepository;

    private final UsersCountRepository usersCountRepository;



    public ResponseEntity<ResultModel> register(String firstname, String lastname, String phone, String os, String currentDate){


        UserValidation userValidation = new UserValidation(usersRepository);
        firstname = firstname.trim();
        lastname = lastname.trim();
        phone = phone.trim();

        userValidation.validate(firstname, lastname, phone);
        Users user = new Users();

        if(userValidation.isFirstnameEmpty()){
            return ResponseEntity.ok(new ResultModel(false,"Ism kiritilmagan"));
        }
        if(userValidation.isLastnameEmpty()){
            return ResponseEntity.ok(new ResultModel(false,"Familiya kiritilmagan"));
        }
        if(userValidation.isPhoneEmpty()){
            return ResponseEntity.ok(new ResultModel(false,"Telefon raqam kiritilmagan"));
        }
        if(!userValidation.isPhoneValid()){
            return ResponseEntity.ok(new ResultModel(false,"Telefon raqam xato kiritilgan"));
        }
        if(userValidation.isUserRegistered()){
            return ResponseEntity.ok(new ResultModel(false,"Ushbu foydalanuvchi ro'yxatdan o'tgan"));
        }


        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPhone(phone);

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
        if(os != null){
            user.setOs(os);
        }
        user.setGenerated(generated);


        LocalDateTime localNow = LocalDateTime.now();
        int tashkentTime = localNow.getHour()+5;
        localNow = LocalDateTime.of(localNow.getYear(),localNow.getMonth(), localNow.getDayOfMonth(),tashkentTime, localNow.getMinute(), localNow.getSecond());
        user.setDate(localNow);
        user.setUpdatedDate(localNow);
        usersRepository.save(user);

        Optional<UsersCount> usersCount = usersCountRepository.findById(1L);

        if(usersCount.isPresent()){
            UsersCount usersCountOriginal = usersCount.get();
            usersCountOriginal.setUserCount(usersCountOriginal.getUserCount()+1);
            usersCountRepository.save(usersCountOriginal);
        }
        else{
            UsersCount usersCount1 = new UsersCount();
            usersCount1.setUserCount(1);
            usersCountRepository.save(usersCount1);
        }


        ResultModelData resultModelData = new ResultModelData();
        resultModelData.setStatus(true);
        resultModelData.setMessage("Siz muvaffaqiyatli ro'yxatdan o'tdingiz");
        resultModelData.setData(user);
        return ResponseEntity.ok(resultModelData);
    }


}
