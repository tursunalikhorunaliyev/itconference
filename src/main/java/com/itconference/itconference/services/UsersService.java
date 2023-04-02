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
import java.util.Objects;
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
}
