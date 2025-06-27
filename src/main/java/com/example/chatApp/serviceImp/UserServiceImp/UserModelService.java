package com.example.chatApp.serviceImp.UserServiceImp;

import com.example.chatApp.model.UserModel;
import com.example.chatApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserModelService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserModel user =  userRepo.findByUsername(username);
       if(user == null){
           throw new UsernameNotFoundException("user not found");
       }
        return new UserPrinciple(user);
    }
}
