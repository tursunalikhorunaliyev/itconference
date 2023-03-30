package com.itconference.itconference.controllers;

import com.itconference.itconference.entities.RandomedUsers;
import com.itconference.itconference.model.ResultModel;
import com.itconference.itconference.model.ResultRandom;
import com.itconference.itconference.repositories.GeneratedCardRepository;
import com.itconference.itconference.services.RandomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("it-conference-kuva")
@AllArgsConstructor
public class RandomController {
    private final GeneratedCardRepository generatedRepository;
    private final RandomService randomService;
    @GetMapping("generate")
    public ResponseEntity<ResultModel> all(){
        return randomService.startRandom();
    }

    @GetMapping("randomed")
    public ResponseEntity<ResultModel> randomed(){
      return randomService.randomed();
    }
}
