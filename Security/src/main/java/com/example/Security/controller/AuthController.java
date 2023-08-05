package com.example.Security.controller;

import com.example.Security.dto.LoginRequest;
import com.example.Security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequest loginRequest){
        System.out.println(loginRequest.getId());
        System.out.println(loginRequest.getPassword());
        return loginService.login_stu(Integer.parseInt(loginRequest.getId()), loginRequest.getPassword());
    }

        @PostMapping("/save")
    public String saveDetails(@RequestParam MultipartFile file){
        return loginService.saveDetails(file);
    }
}
