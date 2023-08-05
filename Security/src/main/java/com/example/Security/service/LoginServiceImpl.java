package com.example.Security.service;

import com.example.Security.entity.LoginDetails;
import com.example.Security.excelReader.LoginCreds;
import com.example.Security.respository.LoginDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginDetailsRepo loginDetailsRepo;

    @Override
    public boolean login_stu(int id, String password) {
        Optional<LoginDetails> loginDetails = loginDetailsRepo.findById(id);
        if(loginDetails.isEmpty()) return false;
        String passwordCheck = loginDetails.get().getPassword();
        return (Objects.equals(passwordCheck, password));
    }

    @Override
    public String saveDetails(MultipartFile file) {
        LoginCreds loginCreds = new LoginCreds();
        try {
            List<LoginDetails> loginDetailsList = loginCreds.converterLogin(file.getInputStream());
            loginDetailsRepo.saveAll(loginDetailsList);
            return "Saved!";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
