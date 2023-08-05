package com.example.Security.service;

import org.springframework.web.multipart.MultipartFile;

public interface LoginService {
    public boolean login_stu(int id, String password);
    public String saveDetails(MultipartFile file);
}
