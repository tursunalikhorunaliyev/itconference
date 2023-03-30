package com.itconference.itconference.controllers;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.repositories.GeneratedCardRepository;
import com.itconference.itconference.repositories.UsersRepository;
import com.itconference.itconference.services.RandomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("it-conference-kuva/random")
@AllArgsConstructor
public class RandomController {
    private final GeneratedCardRepository generatedRepository;
    private final RandomService randomService;
    private final UsersRepository usersRepository;
    @GetMapping("generate")
    public ResponseEntity<ResultModel> all(){
        return randomService.startRandom();
    }

    @GetMapping("randomed")
    public ResponseEntity<ResultModel> randomed(){
      return randomService.randomed();
    }

    @PostMapping("accept-winner")
    public ResponseEntity<ResultModel> acceptWinner(@RequestParam("cardId") Long cardId){
        return randomService.acceptWinner(cardId);
    }
    @GetMapping("users-card")
    public ResponseEntity<List<Long>> usersCard(){
        List<Long> cards = usersRepository.findAll().stream().map(e->e.getGenerated().getCardID()).toList();
        return ResponseEntity.ok(cards);
    }
}
