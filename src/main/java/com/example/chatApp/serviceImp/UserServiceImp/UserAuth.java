package com.example.chatApp.serviceImp.UserServiceImp;

import com.example.chatApp.model.UserModel;
import com.example.chatApp.repository.UserRepo;
import com.example.chatApp.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuth implements UserService {
    @Autowired
    JWTtoken jwTtoken;
    @Autowired
    AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder encode  = new BCryptPasswordEncoder(12);
    @Autowired
    UserRepo userRepo;
    @Override
    public void register(String userName,String email, String password) {
        UserModel user = new UserModel();
        user.setPassword(encode.encode(password));
        user.setUsername(userName);
        user.setEmail(email);
        userRepo.save(user);
    }

    @Override
    public String verify(String userName, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName , password));
        if(authentication.isAuthenticated()){
            return jwTtoken.generateToken(userName);
        }
        return "fail";
    }
}
