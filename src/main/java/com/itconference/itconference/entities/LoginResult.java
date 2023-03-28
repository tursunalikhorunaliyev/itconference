package com.itconference.itconference.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    private String firstname;
    private String lastname;
    private String phone;
    private Long generatedCardId;
}
