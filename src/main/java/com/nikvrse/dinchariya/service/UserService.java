package com.nikvrse.dinchariya.service;

import com.nikvrse.dinchariya.model.User;
import com.nikvrse.dinchariya.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    public UserService(UserRepo userRepo){
        this.userRepo=userRepo;
    }

//    private static final Logger logger=  LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    public void saveUser(User user){
        userRepo.save(user);
    }

    public boolean saveNewUser(User user) {
        try {
            User prevUser = userRepo.findByUsername(user.getUsername());

            if (prevUser == null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRoles(List.of("USER"));
                userRepo.save(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage());
            return false;
        }
    }

    public  void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(user);
    }

    public  void makeAdmin(User user){
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepo.save(user);
    }

    public  void updateUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User getUserById(ObjectId id){
        return userRepo.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
        return  userRepo.findAll();
    }

    public void deleteUserById(ObjectId id){
        userRepo.deleteById(id);
    }

    public  User getUserByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public void deleteUserByUsername(String username){
        userRepo.deleteByUsername(username);
    }

}
