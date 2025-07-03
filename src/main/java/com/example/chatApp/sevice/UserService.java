package com.example.chatApp.sevice;

public interface UserService {

    public void register(String userName ,String email, String password);

   public  String verify(String userName, String password);
}
