package com.itconference.itconference.services;
import com.itconference.itconference.entities.GeneratedCard;
import com.itconference.itconference.entities.RandomedUsers;
import com.itconference.itconference.entities.Users;
import com.itconference.itconference.entities.Winners;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.model.ResultModelData;
import com.itconference.itconference.model.ResultRandom;
import com.itconference.itconference.repositories.GeneratedCardRepository;
import com.itconference.itconference.repositories.RandomedUsersRepository;
import com.itconference.itconference.repositories.UsersRepository;
import com.itconference.itconference.repositories.WinnersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RandomService {
    private final RandomedUsersRepository randomedUsersRepository;
    private final WinnersRepository winnersRepository;

    private final UsersRepository usersRepository;

    private final GeneratedCardRepository generatedCardRepository;
    private final Random random = new Random();

    public RandomService(RandomedUsersRepository randomedUsersRepository, WinnersRepository winnersRepository, UsersRepository usersRepository, GeneratedCardRepository generatedCardRepository) {
        this.randomedUsersRepository = randomedUsersRepository;
        this.winnersRepository = winnersRepository;
        this.usersRepository = usersRepository;
        this.generatedCardRepository = generatedCardRepository;
    }

    private List<Users> usersList = new LinkedList<>();




    public ResponseEntity<ResultModel> startRandom(){


        usersList = usersRepository.findAll();
        if(usersList.size()==0){
            ResultModel resultRandom = new ResultModel();
            resultRandom.setStatus(false);
            resultRandom.setMessage("Random uchun resurs mavjud emas");
            return ResponseEntity.ok(resultRandom);
        }
        if(usersList.size()==randomedUsersRepository.findAll().size()){
            ResultModel resultRandom = new ResultModel();
            resultRandom.setStatus(false);
            resultRandom.setMessage("Barcha random amalga oshirildi");
            return ResponseEntity.ok(resultRandom);
        }

        List<Long> userCardIDs = usersList.stream().map(e-> e.getGenerated().getCardID()).toList();

        int randomIndex = random.nextInt(usersList.size());

        Users randomUser = usersList.get(randomIndex);

        List<Users> allRandomedUsers = randomedUsersRepository
                .findAll()
                .stream()
                .map(RandomedUsers::getUsers)
                .toList();

        while (allRandomedUsers.contains(randomUser)){
            randomIndex = random.nextInt(usersList.size());
            randomUser = usersList.get(randomIndex);
        }

/*        while (randomedUsersRepository.existsById(randomUser.getId())){
            randomIndex = random.nextInt(usersList.size());
            randomUser = usersList.get(randomIndex);
        }*/

        RandomedUsers randomedUsers = new RandomedUsers();
        randomedUsers.setUsers(randomUser);
        randomedUsersRepository.save(randomedUsers);


        ResultRandom resultRandom = new ResultRandom();
        resultRandom.setRandomUser(randomUser);
        resultRandom.setCardIds(userCardIDs);

        ResultModelData resultModelData = new ResultModelData();
        resultModelData.setStatus(true);
        resultModelData.setMessage("Generatsiya muvaffaqiyatli amalga oshirildi");
        resultModelData.setData(resultRandom);


        return ResponseEntity.ok(resultModelData);
    }

    public ResponseEntity<ResultModel> randomed(){

        ResultModelData resultModelData = new ResultModelData(randomedUsersRepository.findAll());
        resultModelData.setStatus(true);
        resultModelData.setMessage("Barcha random qilingan foydalanuvchilar");

        return ResponseEntity.ok(resultModelData);
    }

    public ResponseEntity<ResultModel> acceptWinner(Long cardId){
        Optional<GeneratedCard> generatedCardOptional = generatedCardRepository.findByCardID(cardId);
        if(generatedCardOptional.isPresent()){
            GeneratedCard generatedCard = generatedCardOptional.get();
            Optional<Users> winnerOptional = usersRepository.findByGenerated(generatedCard);
            if(winnerOptional.isPresent()){
                Winners winner = new Winners();
                winner.setUsers(winnerOptional.get());
                winnersRepository.save(winner);
                return ResponseEntity.ok(new ResultModel(true, "G'olib saqlandi"));
            }
            else{
                return ResponseEntity.ok(new ResultModel(true, "Card id topilmadi"));
            }
        }
        else{
            return ResponseEntity.ok(new ResultModel(true, "User topilmadi"));
        }

    }
}
