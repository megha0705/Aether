package com.example.chatApp.controller;

import com.example.chatApp.dto.SubscriptionDTO;
import com.example.chatApp.serviceImp.UserServiceImp.MessageServiceImp;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import nl.martijndwars.webpush.Subscription;
import nl.martijndwars.webpush.Subscription;
@RestController
public class NotificationController {
    @Autowired
    MessageServiceImp messageServiceImp;
    @PostMapping("subscription")
    public void subscription(@RequestBody  SubscriptionDTO dto  , Authentication authentication){
        String userName = authentication.getName();
        Subscription.Keys keys = new Subscription.Keys( dto.getKey().getP256dhKey() ,dto.getKey().getAuthKey() );
        Subscription subscription = new Subscription(dto.getEndpoint() , keys);
        messageServiceImp.subscribe(subscription , userName);

    }
    @PostMapping("unsubscribe")
    public void unsubscribe(@RequestParam  String endpoint , Authentication authentication) {
        messageServiceImp.unsubscribe(endpoint , authentication.getName());
    }
@GetMapping("publicKey")
    public @Nonnull String getPublicKey() {
        return messageServiceImp.getPublicKey();
    }
}
