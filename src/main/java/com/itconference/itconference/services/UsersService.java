package com.itconference.itconference.services;

import com.itconference.itconference.entities.*;
import com.itconference.itconference.model.PageableContentModel;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.model.ResultModelData;
import com.itconference.itconference.model.UsersModel;
import com.itconference.itconference.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersCountRepository usersCountRepository;

    private final DeletedUsersRepository deletedUsersRepository;

    private final RandomedUsersRepository randomedUsersRepository;

    private final WinnersRepository winnersRepository;

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

    public ResponseEntity<ResultModel> deleteUser(Long id, String cause){
        if(!cause.isEmpty()){
            Optional<Users> usersOptional = usersRepository.findById(id);
            if(usersOptional.isPresent()){
                Users userIs = usersOptional.get();
                Optional<RandomedUsers> randomedUsers = randomedUsersRepository.findByUsers(userIs);
                Optional<Winners> winners = winnersRepository.findByUsers(userIs);
                if(randomedUsers.isEmpty() && winners.isEmpty()){
                    DeletedUsers deletedUsers = new DeletedUsers();
                    deletedUsers.setUserId(userIs.getId());
                    deletedUsers.setFirstname(userIs.getFirstname());
                    deletedUsers.setLastname(userIs.getLastname());
                    deletedUsers.setDevice(userIs.getOs());
                    deletedUsers.setPhone(userIs.getPhone());
                    deletedUsers.setGeneratedId(userIs.getGenerated().getCardID());
                    deletedUsers.setCreatedAt(userIs.getDate());
                    deletedUsers.setUpdatedAt(userIs.getUpdatedDate());
                    deletedUsers.setCause(cause);
                    LocalDateTime localNow = LocalDateTime.now();
                    int tashkentTime = localNow.getHour()+5;
                    localNow = LocalDateTime.of(localNow.getYear(),localNow.getMonth(), localNow.getDayOfMonth(),tashkentTime, localNow.getMinute(), localNow.getSecond());
                    deletedUsers.setDeletedAt(localNow);
                    deletedUsersRepository.save(deletedUsers);
                    usersRepository.deleteById(id);
                    return ResponseEntity.ok(new ResultModel(true,"Ma'lumot o'chirildi"));
                }
                else{
                    return ResponseEntity.ok(new ResultModel(false, "Afsuski bu foydalanuvchini o'chira olmaysiz. Chunki bu foydalanuvchi random tizimi orqali generatsiya qilingan yoki g'olib deb topilgan"));
                }
            }
            else{
                return ResponseEntity.ok(new ResultModel(false,"Foydalanuvchi topilmadi"));
            }
        }
        else{
            return ResponseEntity.ok(new ResultModel(false,"Sabab kiritilmadi"));
        }








        //List<UsersCount> usersCount = usersCountRepository.findAll();
/*
        if(usersCount.size()>1){
            throw new RuntimeException();
        }
        else{
           UsersCount usersCountUpdated =  usersCount.get(0);
           usersCountUpdated.setUserCount(usersCountUpdated.getUserCount()-1);
           usersCountRepository.save(usersCountUpdated);
        }*/

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

        LocalDateTime localNow = LocalDateTime.now();
        int tashkentTime = localNow.getHour()+5;
        localNow = LocalDateTime.of(localNow.getYear(),localNow.getMonth(), localNow.getDayOfMonth(),tashkentTime, localNow.getMinute(), localNow.getSecond());
        user.setUpdatedDate(localNow);

        usersRepository.save(user);
        return ResponseEntity.ok(new ResultModel(true,"Foydalanuvchi ma'lumotlari o'zgartirildi"));
    }

    public ResponseEntity<List<Users>> all(){
        return ResponseEntity.ok(usersRepository.findAll());
    }
}
