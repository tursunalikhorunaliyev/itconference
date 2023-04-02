package com.itconference.itconference.services;

import com.itconference.itconference.entities.GeneratedCard;
import com.itconference.itconference.entities.Users;
import com.itconference.itconference.entities.UsersCount;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.model.ResultModelData;
import com.itconference.itconference.model.ResultSucces;
import com.itconference.itconference.repositories.GeneratedCardRepository;
import com.itconference.itconference.repositories.UsersCountRepository;
import com.itconference.itconference.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserRegisterService {
    private final UsersRepository usersRepository;
    private final GeneratedCardRepository generatedRepository;

    private final UsersCountRepository usersCountRepository;


    public ResponseEntity<ResultModel> register(String firstname, String lastname, String phone, String os){
        final List<String> phoneCodes = new LinkedList<>();
        phoneCodes.add("90");
        phoneCodes.add("91");
        phoneCodes.add("93");
        phoneCodes.add("94");
        phoneCodes.add("99");
        phoneCodes.add("33");
        phoneCodes.add("88");
        phoneCodes.add("73");
        phoneCodes.add("95");
        phoneCodes.add("71");

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
            if(phoneOriginal.length()==14 && (phoneCodes.contains(phoneOriginal.substring(1, 3)))){
                user.setPhone(phone);

            }
            else{
                return ResponseEntity.ok(new ResultModel(false, "Telefon raqam xato kiritilgan"));
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
        if(os != null){
            user.setOs(os);
        }
        user.setDate(LocalDateTime.now());
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

        final ResultSucces registerSucces = new ResultSucces();

        registerSucces.setFirstname(firstnameOriginal);
        registerSucces.setLastname(lastnameOriginal);
        registerSucces.setCardID(random_int);

        ResultModelData resultModelData = new ResultModelData(registerSucces);
        resultModelData.setStatus(true);
        resultModelData.setMessage("Siz muvaffaqiyatli ro'yxatdan o'tdingiz");
        return ResponseEntity.ok(resultModelData);
    }


}
