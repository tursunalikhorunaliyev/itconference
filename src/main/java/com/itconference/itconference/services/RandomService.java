package com.itconference.itconference.services;
import com.itconference.itconference.entities.*;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.model.ResultModelData;
import com.itconference.itconference.model.ResultRandom;
import com.itconference.itconference.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RandomService {
    private final RandomedUsersRepository randomedUsersRepository;
    private final WinnersRepository winnersRepository;

    private final UsersRepository usersRepository;

    private final GeneratedCardRepository generatedCardRepository;

    private final SubjectsRepository subjectsRepository;

    private final WinnersSubjectsRepository winnersSubjectsRepository;
    private final Random random = new Random();

    public RandomService(RandomedUsersRepository randomedUsersRepository, WinnersRepository winnersRepository, UsersRepository usersRepository, GeneratedCardRepository generatedCardRepository, SubjectsRepository subjectsRepository, WinnersSubjectsRepository winnersSubjectsRepository) {
        this.randomedUsersRepository = randomedUsersRepository;
        this.winnersRepository = winnersRepository;
        this.usersRepository = usersRepository;
        this.generatedCardRepository = generatedCardRepository;
        this.subjectsRepository = subjectsRepository;
        this.winnersSubjectsRepository = winnersSubjectsRepository;
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

        List<Winners> winners = winnersRepository.findAll();
        List<Long> winnersCardId = winners.stream().map(e-> e.getUsers().getGenerated().getCardID()).toList();

       if(winnersCardId.contains(cardId)){
           return ResponseEntity.ok(new ResultModel(false, "Ushbu qatnashuvchi g'olib deb topilgan"));
       }
       if(winners.size()>=5){
            return ResponseEntity.ok(new ResultModel(false, "Barcha g'oliblar aniqlandi"));
        }
        Optional<GeneratedCard> generatedCardOptional = generatedCardRepository.findByCardID(cardId);
        if(generatedCardOptional.isPresent()){

            GeneratedCard generatedCard = generatedCardOptional.get();
            Optional<Users> winnerOptional = usersRepository.findByGenerated(generatedCard);
            if(winnerOptional.isPresent()){
                Users userTest = winnerOptional.get();
                Optional<RandomedUsers> randomedUsersOptional = randomedUsersRepository.findByUsers(userTest);
                if(randomedUsersOptional.isPresent()){
                    Winners winner = new Winners();
                    winner.setUsers(winnerOptional.get());
                    winnersRepository.save(winner);
                    return ResponseEntity.ok(new ResultModel(true, "G'olib saqlandi"));
                }
                else{
                    return ResponseEntity.ok(new ResultModel(false, "Qatnashchi random qilinmagan"));
                }
            }
            else{
                return ResponseEntity.ok(new ResultModel(false, "Card id topilmadi"));
            }
        }
        else{
            return ResponseEntity.ok(new ResultModel(false, "Qatnashuvchi topilmadi"));
        }

    }

    public ResponseEntity<List<Winners>> allWinners(){
        return ResponseEntity.ok(winnersRepository.findAll());
    }

    public ResponseEntity<ResultModel> winnersWithSubjects(){

        winnersSubjectsRepository.deleteAll();
        List<Subjects> subjects = subjectsRepository.findAll();
        List<Winners> winners = winnersRepository.findAll();

        LinkedList<Integer> generatedRandomNumbers = new LinkedList<>();

        int randomNumber = new Random().nextInt(5);
        while (generatedRandomNumbers.size()!=5){
            while (generatedRandomNumbers.contains(randomNumber)){
                randomNumber = new Random().nextInt(5);
            }
            generatedRandomNumbers.add(randomNumber);
        }

        for(int i=0; i<generatedRandomNumbers.size(); i++){
            WinnersSubjects winnersSubjects = new WinnersSubjects();
            winnersSubjects.setWinners(winners.get(i));
            winnersSubjects.setSubjects(subjects.get(generatedRandomNumbers.get(i)));
            winnersSubjectsRepository.save(winnersSubjects);
        }

        ResultModelData resultModelData = new ResultModelData();
        resultModelData.setStatus(true);
        resultModelData.setMessage("G'oliblar yo'nalishlarga biriktirildi");
        resultModelData.setData(winnersSubjectsRepository.findAll());
        return ResponseEntity.ok(resultModelData);

    }

    public ResponseEntity<List<WinnersSubjects>> allWinnersSubjects(){
        return ResponseEntity.ok(winnersSubjectsRepository.findAll());
    }
}
