package com.example.chatApp.repository;

import com.example.chatApp.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepo extends MongoRepository<UserProfile , String> {
}
