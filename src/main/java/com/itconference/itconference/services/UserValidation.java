package com.itconference.itconference.services;

import com.itconference.itconference.repositories.UsersRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Service

public class UserValidation {
    private boolean isFirstnameEmpty = false;
    private boolean isLastnameEmpty = false;
    private boolean isPhoneValid = false;

    private boolean isPhoneEmpty = false;

    private boolean isUserRegistered = false;

    private final UsersRepository usersRepository;

    public UserValidation(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public void validate(String firstname, String lastname, String phone){
        final List<String> phoneCodes = new LinkedList<>();
        phoneCodes.add("90");
        phoneCodes.add("91");
        phoneCodes.add("93");
        phoneCodes.add("94");
        phoneCodes.add("99");
        phoneCodes.add("33");
        phoneCodes.add("88");
        phoneCodes.add("73");
        phoneCodes.add("95");
        phoneCodes.add("71");

        if(phone.isEmpty()){
            isPhoneEmpty=true;
        }
        else isPhoneValid= phone.trim().length() == 14 && (phoneCodes.contains(phone.substring(1, 3)));

        if(firstname.isEmpty()){
            isFirstnameEmpty=true;
        }
        if(lastname.isEmpty()){
            isLastnameEmpty=true;
        }

        if(usersRepository.existsByPhone(phone)){
            isUserRegistered = true;
        }
        else if(usersRepository.existsByFirstname(firstname) && usersRepository.existsByLastname(lastname)){
            isUserRegistered = true;
        }
    }



}
