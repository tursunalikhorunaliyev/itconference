package com.itconference.itconference.services;

import com.itconference.itconference.entities.Users;
import com.itconference.itconference.entities.UsersCount;
import com.itconference.itconference.model.PageableContentModel;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.model.ResultModelData;
import com.itconference.itconference.model.UsersModel;
import com.itconference.itconference.repositories.UsersCountRepository;
import com.itconference.itconference.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersCountRepository usersCountRepository;

    public ResponseEntity<ResultModel> userByPage(int page, int pageSize){
        Optional<UsersCount> usersCount = usersCountRepository.findById(1L);
        if(usersCount.isPresent()){
            List<Users> usersList = usersRepository.findAll(PageRequest.of(page-1, pageSize)).getContent();
            UsersModel usersModel = new UsersModel(usersCount.get().getUserCount(), new PageableContentModel((long)page,(long)usersList.size(), usersList));
            ResultModelData resultModelData = new ResultModelData(usersModel);
            resultModelData.setStatus(true);
            resultModelData.setMessage("Foydalanuvchilar aniqlandi");
            return ResponseEntity.ok(resultModelData);
        }
        else{
            return ResponseEntity.ok(new ResultModel(false,"Userlar topilmadi"));
        }
    }

    public ResponseEntity<List<Users>> today(){
        List<Users> users = usersRepository.findByIdIn(usersRepository.today());

        return ResponseEntity.ok(users);
    }
    public ResponseEntity<List<Users>> yesterday(){
        List<Users> users = usersRepository.findByIdIn(usersRepository.yesterday());

        return ResponseEntity.ok(users);

    }

    public ResponseEntity<ResultModel> deleteUser(Long id){
        usersRepository.deleteById(id);
        return ResponseEntity.ok(new ResultModel(true,"Ma'lumot o'chirildi"));
    }

    public ResponseEntity<ResultModel> editUser(Long id, String firstname, String lastname, String phone){
        Users user = new Users();
        Optional<Users> usersOptional = usersRepository.findById(id);
        if(usersOptional.isEmpty()){
            return ResponseEntity.ok(new ResultModel(false, "User topilmadi"));
        }
        else{
            user = usersOptional.get();
        }
        firstname = firstname.trim();
        lastname = lastname.trim();
        phone = phone.trim();
        UserValidation userValidation = new UserValidation(usersRepository);
        userValidation.validate(firstname, lastname, phone);

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
        if(userValidation.isUsernameRegistered()){
            return ResponseEntity.ok(new ResultModel(false,"Ushbu ism familiya ro'yxatdan mavjud"));
        }
        if(userValidation.isPhoneRegistered()){
            return ResponseEntity.ok(new ResultModel(false,"Ushbu telefon raqam ro'yxatdan o'tkazilgan"));
        }
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPhone(phone);
        usersRepository.save(user);
        return ResponseEntity.ok(new ResultModel(true,"Foydalanuvchi ma'lumotlari o'zgartirildi"));
    }

    public ResponseEntity<List<Users>> all(){
        return ResponseEntity.ok(usersRepository.findAll());
    }
}
