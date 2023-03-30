package com.itconference.itconference.services;
import com.itconference.itconference.entities.Users;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.model.ResultModelData;
import com.itconference.itconference.model.ResultSucces;
import com.itconference.itconference.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserLoginService {

    private UsersRepository usersRepository;

    public ResponseEntity<ResultModel> login(String phone){
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
        final String phoneOriginal = phone.trim();
        if(phoneOriginal.length()==13 && phoneCodes.contains(phoneOriginal.substring(1,3))){
            Optional<Users> usersOptional = usersRepository.findByPhone(phoneOriginal);
            if(usersOptional.isPresent()){
                Users user = usersOptional.get();
                ResultModelData resultSucces = new ResultModelData();
                resultSucces.setStatus(true);
                resultSucces.setMessage("Foydalanuvchi tizimga kirdi");
                resultSucces.setData(new ResultSucces(user.getFirstname(), user.getFirstname(),user.getGenerated().getCardID()));
                return ResponseEntity.ok(resultSucces);
            }
            else{
                return ResponseEntity.ok(new ResultModel(false, "Bu foydalanuvchi tizimda ro'yxatdan o'tkazilmagan"));
            }
        }
        else{
            return ResponseEntity.ok(new ResultModel(false, "raqam noto'g'ri kiritilgan"));
        }
    }
}
