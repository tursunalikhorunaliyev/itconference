package com.itconference.itconference.model;

import com.itconference.itconference.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultRandom{
    private Users randomUser;
    private List<Long> cardIds;
}
