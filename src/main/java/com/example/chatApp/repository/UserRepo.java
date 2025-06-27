package com.example.chatApp.repository;

import com.example.chatApp.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<UserModel, String> {
    public UserModel findByUsername(String username);
}
