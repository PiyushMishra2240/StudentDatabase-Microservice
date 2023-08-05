package com.example.Security.respository;

import com.example.Security.dto.LoginRequest;
import com.example.Security.entity.LoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDetailsRepo extends JpaRepository<LoginDetails, Integer> {
}
