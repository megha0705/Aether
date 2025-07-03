package com.example.chatApp.repository;

import com.example.chatApp.model.RoomServiceModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomServiceRepo extends MongoRepository<RoomServiceModel, String> {
    public RoomServiceModel findByroomId(String roomId);
    public void deleteByroomId(String roomId);
}
