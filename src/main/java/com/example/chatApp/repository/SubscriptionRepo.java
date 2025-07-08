package com.example.chatApp.repository;

import com.example.chatApp.model.SubscriptionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepo extends MongoRepository<SubscriptionModel , String> {
    public SubscriptionModel findByuserName(String userName);
    public void deleteByuserName(String userName);

  public  List<SubscriptionModel> findByAlluserName(String recieve);
}
