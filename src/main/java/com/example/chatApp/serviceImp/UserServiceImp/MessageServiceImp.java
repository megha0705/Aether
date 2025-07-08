package com.example.chatApp.serviceImp.UserServiceImp;

import com.example.chatApp.model.RoomServiceModel;
import com.example.chatApp.model.SubscriptionModel;
import com.example.chatApp.repository.RoomServiceRepo;
import com.example.chatApp.repository.SubscriptionRepo;
import jakarta.annotation.PostConstruct;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class MessageServiceImp {
@Autowired
    SubscriptionRepo subscriptionRepo;
@Autowired
    RoomServiceRepo roomServiceRepo;
    @Value("${vapid.public.key}")
    private String publicKey;
    @Value("${vapid.private.key}")
    private String privateKey;

    private PushService pushService;
  //  private List<Subscription> subscriptions = new ArrayList<>();

    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(publicKey, privateKey);
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void subscribe(Subscription subscription , String userName) {
        System.out.println("Subscribed to " + subscription.endpoint);
        SubscriptionModel sub = new SubscriptionModel();
        sub.setEndpoint(subscription.endpoint);
        sub.setP256dhKey(subscription.keys.p256dh);
        sub.setAuthKey(subscription.keys.auth);
        sub.setUserName(userName);
        subscriptionRepo.save(sub);
        //this.subscriptions.add(subscription);

    }

    public void unsubscribe(String endpoint , String userName) {
        System.out.println("Unsubscribed from " + endpoint);
        SubscriptionModel sub = subscriptionRepo.findByuserName(userName);
        if(sub != null){
            subscriptionRepo.deleteByuserName(userName);
        }else{
            throw new RuntimeException("subscription model is null");
        }
       // subscriptions = subscriptions.stream().filter(s -> !endpoint.equals(s.endpoint))
        //        .collect(Collectors.toList());


    }

    public void sendNotification(Subscription subscription, String messageJson) {
        try {
            pushService.send(new Notification(subscription, messageJson));
        } catch (GeneralSecurityException | IOException | JoseException | ExecutionException
                 | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendNotifications(String sender , String roomId) {
        System.out.println("Sending notifications to all subscribers");
        RoomServiceModel room = roomServiceRepo.findByroomId(roomId);
        List<String> notificationReciever = new ArrayList<>();
        for(String reciever : room.getParticipantsId()){
            if(!reciever.equals(sender)){
                notificationReciever.add(reciever);
            }
        }
        String json = """
        { "title": "New Message", "body": "You have a new message in room: %s" }
    """.formatted(room.getRoomName());


        for(String recieve : notificationReciever){
            List<SubscriptionModel> subs = subscriptionRepo.findByAlluserName(recieve);
            for(SubscriptionModel sub : subs){
                Subscription.Keys keys = new Subscription.Keys(sub.getP256dhKey() , sub.getAuthKey());
                Subscription subscription = new Subscription(sub.getEndpoint() , keys);
                sendNotification(subscription, json);
            }

        }


    }
}
