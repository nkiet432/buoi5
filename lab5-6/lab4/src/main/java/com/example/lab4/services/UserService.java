package com.example.lab4.services;

import com.example.lab4.entity.User;
import com.example.lab4.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    public void save (User user){
        userRepository.save(user);
    }
}
