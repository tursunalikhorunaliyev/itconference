package com.itconference.itconference.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableContentModel {
    private Long page;
    private Long usersCount;
    private Object users;

}
