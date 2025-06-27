package com.example.chatApp.repository;

import com.example.chatApp.model.ChatMessage;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
@Repository
public interface ChatMesaageRepo extends MongoRepository<ChatMessage, String>{
}
